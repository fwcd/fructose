package com.fwcd.fructose.genetic.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.fwcd.fructose.genetic.operators.Encoder;
import com.fwcd.fructose.genetic.operators.GaussianFloatMutator;
import com.fwcd.fructose.genetic.operators.Mutator;
import com.fwcd.fructose.structs.FloatList;

/**
 * A basic, selection-based population that uses
 * float[] as it's genotype and relies on manually
 * set fitness values.
 * 
 * @author Fredrik
 *
 */
public class ManualPopulation extends TemplatePopulation<float[]> {
	private final Map<float[], Float> fitnesses = new HashMap<>();
	private Mutator<float[]> mutator = new GaussianFloatMutator();
	private int survivorsPerGeneration = 5;
	
	private float selectorEpsilon = 0.5F; // The probability that random genes will be selected instead of those with the highest fitness
	
	public void setSelectorEpsilon(float epsilon) {
		selectorEpsilon = epsilon;
	}
	
	public void setMutator(Mutator<float[]> mutator) {
		this.mutator = mutator;
	}
	
	public void setSurvivorsPerGeneration(int survivors) {
		survivorsPerGeneration = survivors;
	}
	
	@Override
	protected float getFitness(float[] genes) {
		return fitnesses.getOrDefault(genes, Float.NEGATIVE_INFINITY);
	}
	
	public void setFitness(float[] genes, float value) {
		fitnesses.put(genes, value);
	}
	
	public <P> void setFitness(P phenes, float value, Encoder<float[], P> encoder) {
		fitnesses.put(encoder.encode(phenes), value);
	}

	@Override
	public void evolve() {
		List<float[]> genes = getAllGenes();
		sortByFitnessDescending(genes);
		
		int geneCount = genes.size();
		mutate(genes, survivorsPerGeneration, geneCount);
	}
	
	/**
	 * Mutates the genes in the given range.
	 * 
	 * @param genes - All genes
	 * @param startIndex - The start index (inclusive)
	 * @param endIndex - The end index (exclusive)
	 */
	private void mutate(List<float[]> genes, int startIndex, int endIndex) {
		Random r = ThreadLocalRandom.current();
		float mutationChance = getMutationChance();
		
		for (int i=startIndex; i<endIndex; i++) {
			if (r.nextFloat() < mutationChance) {
				mutator.mutateInPlace(genes.get(i));
			}
		}
	}

	private void sortByFitnessDescending(List<float[]> genes) {
		genes.sort((a, b) -> Float.compare(getFitness(b), getFitness(a)));
	}

	public void saveIndividualsTo(File folder, String namePrefix) {
		if (!folder.exists() || !folder.isDirectory()) {
			throw new IllegalArgumentException(folder.toString() + " is not a directory.");
		}
		
		final List<float[]> genes = getAllGenes();
		for (int i=0; i<genes.size(); i++) {
			File file = getFile(folder, namePrefix, i);
			
			try (FileOutputStream fos = new FileOutputStream(file); DataOutputStream dos = new DataOutputStream(fos)) {
				for (float v : genes.get(i)) {
					dos.writeFloat(v);
				}
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}
	
	public void loadIndividualsFrom(File folder, String namePrefix, int amount) {
		if (!folder.exists() || !folder.isDirectory()) {
			throw new IllegalArgumentException(folder.toString() + " is not a directory.");
		}
		
		final List<float[]> genes = new ArrayList<>();
		for (int i=0; i<genes.size(); i++) {
			File file = getFile(folder, namePrefix, i);
			
			try (FileInputStream fis = new FileInputStream(file); DataInputStream dis = new DataInputStream(fis)) {
				FloatList individual = new FloatList();
				
				while (dis.available() > 0) {
					individual.add(dis.readFloat());
				}
				
				genes.add(individual.toArray());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		
		setAllGenes(genes);
	}

	private File getFile(File folder, String namePrefix, int i) {
		return new File(folder.getAbsolutePath() + "/" + namePrefix + Integer.toString(i));
	}

	@Override
	public String toString() {
		String s = "Population:\n";
		
		for (float[] genes : getAllGenes()) {
			s += Float.toString(getFitness(genes)) + "\t-> " + Arrays.toString(genes) + "\n";
		}
		
		return s;
	}

	@Override
	public float[] selectBestGenes() {
		Random random = ThreadLocalRandom.current();
		List<float[]> allGenes = getAllGenes();
		
		// Use epsilon-greedy strategy to select genes
		if (random.nextFloat() < selectorEpsilon) {
			return allGenes.get(random.nextInt(allGenes.size()));
		} else {
			return Collections.max(allGenes, (a, b) -> Float.compare(getFitness(a), getFitness(b)));
		}
	}
}
