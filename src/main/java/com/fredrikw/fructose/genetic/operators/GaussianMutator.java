package com.fredrikw.fructose.genetic.operators;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GaussianMutator implements Mutator {
	private static final long serialVersionUID = 23465873645873645L;
	
	private int bound = Integer.MAX_VALUE;
	private float multiplier = 1;
	private float bias = 0;
	
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
	
	public void setBound(int bound) {
		this.bound = bound;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	public void setBias(float bias) {
		this.bias = bias;
	}
}
