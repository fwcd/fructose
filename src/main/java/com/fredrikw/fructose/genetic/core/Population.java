package com.fredrikw.fructose.genetic.core;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import com.fredrikw.fructose.Distribution;
import com.fredrikw.fructose.genetic.operators.Crossover;
import com.fredrikw.fructose.genetic.operators.Decoder;
import com.fredrikw.fructose.genetic.operators.FitnessFunction;
import com.fredrikw.fructose.genetic.operators.Mutator;

public class Population implements Serializable {
	private static final long serialVersionUID = -147928374982734L;
	private float[][] genotype;
	
	private Crossover crossover;
	private FitnessFunction fitnessFunc;
	private Mutator mutator;
	
	private float mutationChance; // Between 0 and 1
	private int generation = 0;
	
	public Population(
			Crossover crossover,
			FitnessFunction fitnessFunc,
			Mutator mutator,
			float[][] genotype,
			float mutationChance
	) {
		this.crossover = crossover;
		this.fitnessFunc = fitnessFunc;
		this.mutator = mutator;
		this.genotype = genotype;
	}
	
	public void setMutationChance(float chance) {
		mutationChance = chance;
	}
	
	/**
	 * @return The amount of individuals
	 */
	public int size() {
		return genotype.length;
	}
	
	public int getGeneration() {
		return generation;
	}
	
	public <T> T getIndividualPhenes(Decoder<T> decoder, int index) {
		return decoder.decode(genotype[index]);
	}
	
	public float[] getIndividualGenes(int index) {
		return genotype[index];
	}
	
	public void setGenotype(float[][] genotype) {
		this.genotype = genotype;
	}
	
	public float[][] getGenotype() {
		return genotype;
	}
	
	/**
	 * Selects an individual stochastically from the genotype using a
	 * weighted distribution based of the fitness values.
	 * 
	 * @return The index of the individual in the genotype
	 */
	private int select() {
		Distribution<float[]> dist = new Distribution<>();
		
		for (float[] individual : genotype) {
			dist.add(individual, fitnessFunc.getFitness(individual));
		}
		
		return dist.pickIndexStochastically();
	}
	
	/**
	 * Mutates every individual with the given chance (mutationChance).
	 */
	private void mutate() {
		Random random = ThreadLocalRandom.current();
		
		for (int i=0; i<genotype.length; i++) {
			if (random.nextFloat() < mutationChance) {
				float[] mutation = mutator.mutate(genotype[i]);
				genotype[i] = mutation;
			}
		}
	}
	
	public void evolve(int generations) {
		for (int i=0; i<generations; i++) {
			evolve();
		}
	}
	
	/**
	 * Evolves this population to the next generation. This
	 * includes selection, crossover and mutation.
	 */
	public void evolve() {
		int parentA = select();
		int parentB;
		
		do {
			parentB = select();
		} while (parentA == parentB); // Require two distinct parents
		
		float[] childA = crossover.crossover(genotype[parentA], genotype[parentB]);
		float[] childB = crossover.crossover(genotype[parentA], genotype[parentB]);
		
		genotype[parentA] = childA;
		genotype[parentB] = childB;
		
		mutate();
		generation++;
	}
	
	public float[] getFittestGenes() {
		float[] bestGenes = null;
		float maxFitness = Float.NEGATIVE_INFINITY;
		
		for (float[] genes : genotype) {
			float fitness = fitnessFunc.getFitness(genes);
			
			if (fitness > maxFitness) {
				maxFitness = fitness;
				bestGenes = genes;
			}
		}
		
		if (bestGenes != null) {
			return bestGenes;
		} else {
			throw new NoSuchElementException("Can't fetch the fittest genes when the genotype is empty!");
		}
	}
	
	public <T> T getFittestPhenes(Decoder<T> decoder) {
		return decoder.decode(getFittestGenes());
	}
	
	public Stream<float[]> streamGenes() {
		return Arrays.stream(genotype);
	}
	
	public <T> Stream<T> streamPhenes(Decoder<T> decoder) {
		return streamGenes().map(decoder::decode);
	}
	
	@Override
	public String toString() {
		String s = "Population:\n";
		
		for (float[] genes : genotype) {
			s += Arrays.toString(genes) + "\n";
		}
		
		return s;
	}
}
