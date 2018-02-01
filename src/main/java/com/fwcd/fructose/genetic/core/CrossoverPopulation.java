package com.fwcd.fructose.genetic.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import com.fwcd.fructose.Distribution;
import com.fwcd.fructose.function.ToFloatFunction;
import com.fwcd.fructose.genetic.operators.Crossover;
import com.fwcd.fructose.genetic.operators.Decoder;
import com.fwcd.fructose.genetic.operators.Encoder;
import com.fwcd.fructose.genetic.operators.FitnessFunction;
import com.fwcd.fructose.genetic.operators.Mutator;
import com.fwcd.fructose.text.StringUtils;

/**
 * A simple population implementation that uses
 * crossover to replace the two fittest individuals
 * with their offspring every generation.
 * 
 * @author Fredrik
 *
 * @param <G> - The genotype
 */
public class CrossoverPopulation<G> implements Population<G> {
	private List<G> individuals;
	private Crossover<G> crossover;
	private FitnessFunction<G> fitnessFunc;
	private Mutator<G> mutator;
	
	private float mutationChance; // Between 0 and 1
	private int generation = 0;
	
	public CrossoverPopulation(
			Crossover<G> crossover,
			FitnessFunction<G> fitnessFunc,
			Mutator<G> mutator,
			List<G> individuals,
			float mutationChance
	) {
		this.crossover = crossover;
		this.fitnessFunc = fitnessFunc;
		this.mutator = mutator;
		this.individuals = individuals;
	}
	
	@Override
	public void setMutationChance(float chance) {
		mutationChance = chance;
	}
	
	/**
	 * @return The amount of individuals
	 */
	@Override
	public int size() {
		return individuals.size();
	}
	
	@Override
	public int getGeneration() {
		return generation;
	}
	
	@Override
	public G getIndividualGenes(int index) {
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
	
	/**
	 * Selects an individual stochastically from the genotype using a
	 * weighted distribution based of the fitness values.
	 * 
	 * @return The index of the individual in the genotype
	 */
	private int select() {
		Distribution<G> dist = new Distribution<>();
		
		for (G individual : individuals) {
			dist.add(individual, fitnessFunc.getFitness(individual));
		}
		
		return dist.pickIndexStochastically();
	}
	
	/**
	 * Mutates every individual with the given chance (mutationChance).
	 */
	private void mutate() {
		final Random random = ThreadLocalRandom.current();
		
		int individualsCount = individuals.size();
		for (int i=0; i<individualsCount; i++) {
			if (random.nextFloat() < mutationChance) {
				G mutation = mutator.mutate(individuals.get(i));
				individuals.set(i, mutation);
			}
		}
	}
	
	/**
	 * Evolves this population to the next generation. This
	 * includes selection, crossover and mutation.
	 */
	@Override
	public void evolve() {
		int parentA = select();
		int parentB;
		
		do {
			parentB = select();
		} while (parentA == parentB); // Require two distinct parents
		
		G childA = crossover.crossover(individuals.get(parentA), individuals.get(parentB));
		G childB = crossover.crossover(individuals.get(parentA), individuals.get(parentB));
		
		individuals.set(parentA, childA);
		individuals.set(parentB, childB);
		
		mutate();
		generation++;
	}
	
	@Override
	public G getFittestGenes() {
		G bestGenes = null;
		float maxFitness = Float.NEGATIVE_INFINITY;
		
		for (G genes : individuals) {
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
	
	@Override
	public String toString() {
		String s = "Population:\n";
		
		for (G genes : individuals) {
			s += StringUtils.toString(genes) + "\n";
		}
		
		return s;
	}
	
	public static class Builder<G> {
		private Crossover<G> crossoverFunc = null;
		private Mutator<G> mutator = null;
		private FitnessFunction<G> fitnessFunc = null;
		private float mutationChance = 0.1F;
		private Set<G> genes = new HashSet<>();
		
		public Builder<G> crossoverFunc(Crossover<G> crossoverFunc) {
			this.crossoverFunc = crossoverFunc;
			return this;
		}
		
		public Builder<G> mutator(Mutator<G> mutator) {
			this.mutator = mutator;
			return this;
		}
		
		public <P> Builder<G> fitnessFunc(Decoder<G, P> decoder, ToFloatFunction<P> pheneFitnessFunc) {
			fitnessFunc = genes -> pheneFitnessFunc.apply(decoder.decode(genes));
			return this;
		}
		
		public Builder<G> fitnessFunc(FitnessFunction<G> fitnessFunc) {
			this.fitnessFunc = fitnessFunc;
			return this;
		}
		
		public Builder<G> mutationChance(float chance) {
			this.mutationChance = chance;
			return this;
		}
		
		public <P> Builder<G> spawnIndividuals(Encoder<G, P> encoder, Supplier<P> spawner, int count) {
			for (int i=0; i<count; i++) {
				genes.add(encoder.encode(spawner.get()));
			}
			return this;
		}
		
		public Population<G> build() {
			if (crossoverFunc == null) {
				throw new IllegalStateException("Missing crossover function.");
			} else if (fitnessFunc == null) {
				throw new IllegalStateException("Missing fitness function.");
			} else if (mutator == null) {
				throw new IllegalStateException("Missing mutator function.");
			}
			
			return new CrossoverPopulation<>(crossoverFunc, fitnessFunc, mutator, new ArrayList<>(genes), mutationChance);
		}
	}
}
