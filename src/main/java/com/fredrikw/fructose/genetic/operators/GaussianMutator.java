package com.fredrikw.fructose.genetic.operators;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GaussianMutator implements Mutator {
	private static final long serialVersionUID = 23465873645873645L;
	
	private float bound = Float.POSITIVE_INFINITY;
	private float multiplier = 1;
	private float bias = 0;
	
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
	
	public void setBound(float bound) {
		this.bound = bound;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	public void setBias(float bias) {
		this.bias = bias;
	}
}
