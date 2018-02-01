package com.fwcd.fructose.test.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.fwcd.fructose.genetic.core.CrossoverPopulation;
import com.fwcd.fructose.genetic.core.PopulationManager;
import com.fwcd.fructose.genetic.core.SimplePopulation;
import com.fwcd.fructose.genetic.operators.Decoder;
import com.fwcd.fructose.genetic.operators.Encoder;
import com.fwcd.fructose.genetic.operators.GaussianFloatMutator;
import com.fwcd.fructose.genetic.operators.UniformFloatCrossover;

public class GeneticAlgorithmDemo {
	private static final List<Stone> TOTAL_STONES = Arrays.asList(
			new Stone(4, 12),
			new Stone(2, 2),
			new Stone(1, 1),
			new Stone(2, 1),
			new Stone(10, 4)
	);
	
	private static class Stone {
		private final float value;
		private final float weight;

		public Stone(float value, float weight) {
			this.value = value;
			this.weight = weight;
		}
		
		public float getWeight() {
			return weight;
		}
		
		public float getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return Float.toString(value) + "$ at " + Float.toString(weight) + " kg";
		}
	}
	
	private static class KnapsackSolution {
		private final List<Stone> stones;
		
		public KnapsackSolution(float[] encoded) {
			stones = new ArrayList<>();
			
			int i = 0;
			while (i < encoded.length && encoded[i] != 0) {
				float value = encoded[i++];
				float weight = encoded[i++];
				stones.add(new Stone(value, weight));
			}
		}
		
		public KnapsackSolution(List<Stone> stones) {
			this.stones = stones;
		}
		
		public boolean isAllowed() {
			return stones.size() > 0 && stones.stream()
					.mapToDouble(Stone::getWeight)
					.sum() <= 15;
		}
		
		public float fitness() {
			return (float) stones.stream()
					.mapToDouble(Stone::getValue)
					.sum();
		}

		public float[] encode() {
			float[] result = new float[TOTAL_STONES.size() * 2];
			
			int i = 0;
			for (Stone stone : stones) {
				result[i++] = stone.getValue();
				result[i++] = stone.getWeight();
			}
			
			return result;
		}
		
		@Override
		public String toString() {
			return stones.toString();
		}
	}
	
	private static KnapsackSolution getPossibleSolution() {
		return new KnapsackSolution(TOTAL_STONES.stream()
				.filter(x -> ThreadLocalRandom.current().nextBoolean())
				.collect(Collectors.toList()));
	}
	
	public static void main(String[] args) {
		PopulationManager<float[]> mgr = new PopulationManager<>();
		Encoder<float[], KnapsackSolution> encoder = KnapsackSolution::encode;
		Decoder<float[], KnapsackSolution> decoder = KnapsackSolution::new;
		Supplier<KnapsackSolution> supplier = () -> {
			KnapsackSolution result;
			
			do {
				result = getPossibleSolution();
			} while (!result.isAllowed());
			
			return result;
		};
		
		mgr.set(new CrossoverPopulation.Builder<float[]>()
				.fitnessFunc(decoder, KnapsackSolution::fitness)
				.crossoverFunc(new UniformFloatCrossover())
				.mutator(new GaussianFloatMutator())
				.spawnIndividuals(encoder, supplier, 100)
				.build()
		);
		
		System.out.println(mgr.get());
		
		for (int i=0; i<1000; i++) {
			mgr.get().evolve();
			System.out.println("Generation " + mgr.get().getGeneration() + ": " + mgr.get());
		}
		
		// TODO: Still outputting obviously wrong solutions, because mutation
		// and crossover can destroy the semantics of the encoded results.
		
		System.out.println(mgr.get().getFittestPhenes(decoder));
	}
}
