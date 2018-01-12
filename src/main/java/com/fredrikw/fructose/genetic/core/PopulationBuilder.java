package com.fredrikw.fructose.genetic.core;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import com.fredrikw.fructose.function.ToFloatFunction;
import com.fredrikw.fructose.genetic.operators.Crossover;
import com.fredrikw.fructose.genetic.operators.Decoder;
import com.fredrikw.fructose.genetic.operators.Encoder;
import com.fredrikw.fructose.genetic.operators.FitnessFunction;
import com.fredrikw.fructose.genetic.operators.GaussianMutator;
import com.fredrikw.fructose.genetic.operators.Mutator;
import com.fredrikw.fructose.genetic.operators.UniformCrossover;

public class PopulationBuilder {
	private Crossover crossoverFunc = new UniformCrossover();
	private Mutator mutator = new GaussianMutator();
	private FitnessFunction fitnessFunc;
	private float mutationChance = 0.1F;
	private Set<float[]> genotype = new HashSet<>();
	
	public PopulationBuilder crossoverFunc(Crossover crossoverFunc) {
		this.crossoverFunc = crossoverFunc;
		return this;
	}
	
	public PopulationBuilder mutator(Mutator mutator) {
		this.mutator = mutator;
		return this;
	}
	
	public <T> PopulationBuilder fitnessFunc(Decoder<T> decoder, ToFloatFunction<T> decodedFitnessFunc) {
		fitnessFunc = genes -> decodedFitnessFunc.apply(decoder.decode(genes));
		return this;
	}
	
	public PopulationBuilder fitnessFunc(FitnessFunction fitnessFunc) {
		this.fitnessFunc = fitnessFunc;
		return this;
	}
	
	public PopulationBuilder mutationChance(float chance) {
		this.mutationChance = chance;
		return this;
	}
	
	public <T> PopulationBuilder spawnIndividuals(Encoder<T> encoder, Supplier<T> supplier, int count) {
		for (int i=0; i<count; i++) {
			float[] genes = encoder.encode(supplier.get());
			
			if (genes.length == 0) {
				throw new UnsupportedOperationException("Individual can't have a gene sequence length of 0!");
			}
			
			genotype.add(genes);
		}
		return this;
	}
	
	public Population build() {
		if (crossoverFunc == null) {
			throw new IllegalStateException("Missing crossover function.");
		} else if (fitnessFunc == null) {
			throw new IllegalStateException("Missing fitness function.");
		} else if (mutator == null) {
			throw new IllegalStateException("Missing mutator function.");
		}
		
		return new Population(crossoverFunc, fitnessFunc, mutator, genotype.toArray(new float[0][0]), mutationChance);
	}
}
