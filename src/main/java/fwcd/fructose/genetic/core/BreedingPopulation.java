package fwcd.fructose.genetic.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import fwcd.fructose.Distribution;
import fwcd.fructose.function.ToFloatFunction;
import fwcd.fructose.genetic.operators.Crossover;
import fwcd.fructose.genetic.operators.Decoder;
import fwcd.fructose.genetic.operators.Encoder;
import fwcd.fructose.genetic.operators.FitnessFunction;
import fwcd.fructose.genetic.operators.Mutator;

/**
 * A simple population implementation that uses
 * crossover to replace the two fittest individuals
 * with their offspring every generation.
 * 
 * @author Fredrik
 *
 * @param <G> - The genotype
 */
public class BreedingPopulation<G> extends TemplatePopulation<G> {
	private Crossover<G> crossover;
	private Mutator<G> mutator;
	
	public BreedingPopulation(
			Crossover<G> crossover,
			FitnessFunction<G> fitnessFunc,
			Mutator<G> mutator,
			List<G> individuals,
			float mutationChance
	) {
		setFitnessFunction(fitnessFunc);
		this.crossover = crossover;
		this.mutator = mutator;
		setAllGenes(individuals);
	}
	
	/**
	 * Selects an individual stochastically from the genotype using a
	 * weighted distribution based of the fitness values.
	 * 
	 * @return The index of the individual in the genotype
	 */
	private int select() {
		Distribution<G> dist = new Distribution<>();
		
		for (G individual : getAllGenes()) {
			System.out.println(Arrays.toString((float[]) individual));
			dist.add(individual, Math.max(getFitness(individual), 0));
		}
		
		return dist.pickIndexStochastically();
	}
	
	/**
	 * Mutates every individual with the given chance (mutationChance).
	 */
	private void mutate() {
		final Random random = ThreadLocalRandom.current();
		final List<G> individuals = getAllGenes();
		final float mutationChance = getMutationChance();
		
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
		
		
		G childA = crossover.crossover(getGenes(parentA), getGenes(parentB));
		G childB = crossover.crossover(getGenes(parentA), getGenes(parentB));
		
		setGenes(parentA, childA);
		setGenes(parentB, childB);
		
		mutate();
		incrementGeneration();
	}
	
	@Override
	public G selectBestGenes() {
		G bestGenes = null;
		float maxFitness = Float.NEGATIVE_INFINITY;
		
		for (G genes : getAllGenes()) {
			float fitness = getFitness(genes);
			
			if (fitness > maxFitness) {
				maxFitness = fitness;
				bestGenes = genes;
			}
		}
		
		if (bestGenes != null) {
			return bestGenes;
		} else {
			throw new NoSuchElementException("Can't fetch the fittest genes when there are no individuals!");
		}
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
			
			return new BreedingPopulation<>(crossoverFunc, fitnessFunc, mutator, new ArrayList<>(genes), mutationChance);
		}
	}
}
