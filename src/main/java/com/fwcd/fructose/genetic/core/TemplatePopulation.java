package com.fwcd.fructose.genetic.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.fwcd.fructose.exception.Rethrow;
import com.fwcd.fructose.genetic.operators.FitnessFunction;
import com.fwcd.fructose.text.StringUtils;

public abstract class TemplatePopulation<G> implements Population<G> {
	private final FitnessFunction<G> fitnessFunc;
	private List<G> individuals = new ArrayList<>();
	private float mutationChance = 0.1F;
	private int generation = 0;
	
	public TemplatePopulation(FitnessFunction<G> fitnessFunc) {
		this.fitnessFunc = fitnessFunc;
	}

	@Override
	public void setMutationChance(float chance) {
		mutationChance = chance;
	}
	
	protected float getMutationChance() {
		return mutationChance;
	}

	@Override
	public int size() {
		return individuals.size();
	}

	protected void setGeneration(int generation) {
		this.generation = generation;
	}
	
	protected float getFitness(G genes) {
		return fitnessFunc.getFitness(genes);
	}
	
	protected void incrementGeneration() {
		generation++;
	}
	
	@Override
	public int getGeneration() {
		return generation;
	}

	protected void setGenes(int index, G genes) {
		individuals.set(index, genes);
	}
	
	@Override
	public G getGenes(int index) {
		return individuals.get(index);
	}

	@Override
	public void setAllGenes(List<G> individuals) {
		this.individuals = individuals;
	}

	@Override
	public List<G> getAllGenes() {
		return individuals;
	}

	@Override
	public G getFittestGenes() {
		float maxFitness = Float.NEGATIVE_INFINITY;
		G fittestGenes = null;
		
		for (G individual : individuals) {
			float fitness = fitnessFunc.getFitness(individual);
			if (fitness > maxFitness) {
				maxFitness = fitness;
				fittestGenes = individual;
			}
		}
		
		if (fittestGenes == null) {
			throw new NoSuchElementException();
		} else {
			return fittestGenes;
		}
	}

	@Override
	public void saveTo(OutputStream out) {
		try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.writeObject(individuals);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadFrom(InputStream in) {
		try (ObjectInputStream ois = new ObjectInputStream(in)) {
			individuals = (List<G>) ois.readObject();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (ClassNotFoundException e) {
			throw new Rethrow(e);
		}
	}
	
	@Override
	public String toString() {
		String s = "Population:\n";
		
		for (G genes : individuals) {
			s += StringUtils.toString(genes) + "\n";
		}
		
		return s;
	}
}
