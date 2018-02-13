package com.fwcd.fructose.genetic.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.fwcd.fructose.genetic.operators.FitnessFunction;
import com.fwcd.fructose.genetic.operators.GaussianFloatMutator;
import com.fwcd.fructose.genetic.operators.Mutator;
import com.fwcd.fructose.structs.FloatList;

/**
 * A basic, selection-based population that uses
 * float[] as it's genotype.
 * 
 * @author Fredrik
 *
 */
public class SelectionPopulation extends TemplatePopulation<float[]> {
	private Mutator<float[]> mutator = new GaussianFloatMutator();
	private int survivorsPerGeneration = 5;
	
	public SelectionPopulation(FitnessFunction<float[]> fitnessFunc) {
		super(fitnessFunc);
	}
	
	public void setMutator(Mutator<float[]> mutator) {
		this.mutator = mutator;
	}
	
	public void setSurvivorsPerGeneration(int survivors) {
		survivorsPerGeneration = survivors;
	}

	@Override
	public void evolve() {
		List<float[]> genes = getAllGenes();
		sortByFitnessDescending(genes);
		
		int geneCount = genes.size();
		mutate(genes, geneCount - survivorsPerGeneration, geneCount);
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
				genes.set(i, mutator.mutate(genes.get(i)));
			}
		}
	}

	private void sortByFitnessDescending(List<float[]> genes) {
		Map<float[], Float> fitnesses = new HashMap<>();
		
		for (float[] gene : genes) {
			fitnesses.put(gene, getFitness(gene));
		}
		
		genes.sort((a, b) -> fitnesses.get(b).compareTo(fitnesses.get(a)));
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
	}

	private File getFile(File folder, String namePrefix, int i) {
		return new File(folder.getAbsolutePath() + "/" + namePrefix + Integer.toString(i));
	}
}
