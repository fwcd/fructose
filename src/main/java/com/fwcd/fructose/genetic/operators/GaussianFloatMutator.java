package com.fwcd.fructose.genetic.operators;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GaussianFloatMutator implements Mutator<float[]> {
	private static final long serialVersionUID = 23465873645873645L;
	
	private final float bound;
	private final float multiplier;
	private final float bias;

	public GaussianFloatMutator() {
		bound = Float.POSITIVE_INFINITY;
		multiplier = 5;
		bias = 0;
	}
	
	public GaussianFloatMutator(float bound, float multiplier, float bias) {
		this.bound = bound;
		this.multiplier = multiplier;
		this.bias = bias;
	}
	
	private float probability(float[] genes) {
		return 1F / genes.length;
	}
	
	@Override
	public float[] mutate(float[] genes) {
		Random random = ThreadLocalRandom.current();
		float probability = probability(genes);
		float[] result = new float[genes.length];
		
		for (int i=0; i<genes.length; i++) {
			if (random.nextFloat() < probability) {
				result[i] += (random.nextGaussian() * multiplier) + bias;
				
				if (result[i] > bound) {
					result[i] = bound;
				}
			}
		}
		
		return result;
	}
}
