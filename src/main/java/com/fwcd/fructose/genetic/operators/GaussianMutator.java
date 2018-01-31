package com.fwcd.fructose.genetic.operators;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GaussianMutator implements Mutator {
	private static final long serialVersionUID = 23465873645873645L;
	
	private final int bound;
	private final float multiplier;
	private final float bias;

	public GaussianMutator() {
		bound = Integer.MAX_VALUE;
		multiplier = 5;
		bias = 0;
	}
	
	public GaussianMutator(int bound, float multiplier, float bias) {
		this.bound = bound;
		this.multiplier = multiplier;
		this.bias = bias;
	}
	
	private float probability(int[] genes) {
		return 1F / genes.length;
	}
	
	@Override
	public int[] mutate(int[] genes) {
		Random random = ThreadLocalRandom.current();
		float probability = probability(genes);
		int[] result = new int[genes.length];
		
		for (int i=0; i<genes.length; i++) {
			if (random.nextFloat() < probability) {
				result[i] += (int) ((random.nextGaussian() * multiplier) + bias);
				
				if (result[i] > bound) {
					result[i] = bound;
				}
			}
		}
		
		return result;
	}
}
