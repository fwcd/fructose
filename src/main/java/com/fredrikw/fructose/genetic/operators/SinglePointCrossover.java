package com.fredrikw.fructose.genetic.operators;

import java.util.concurrent.ThreadLocalRandom;

public class SinglePointCrossover implements Crossover {
	private static final long serialVersionUID = 5698219405477450864L;
	
	private float fixedOffsetPercent = 0.5F;
	private boolean randomlySwapParents = true;
	private boolean randomlyChooseOffset = true; // Will ignore offsetPercent if true
	
	@Override
	public int[] crossover(int[] genesA, int[] genesB) {
		if (genesA.length != genesB.length) {
			throw new IllegalArgumentException("Both gene sequences should be of the same length!");
		}
		
		ThreadLocalRandom random = ThreadLocalRandom.current();
		
		boolean swapParents = randomlySwapParents ? random.nextBoolean() : false;
		float offsetPercent = randomlyChooseOffset ? random.nextFloat() : fixedOffsetPercent;
		
		int length = genesA.length;
		int offset = (int) (length * offsetPercent);
		int[] result = new int[length];
		
		for (int i=0; i<length; i++) {
			if ((i < offset) ^ swapParents) {
				result[i] = genesA[i];
			} else {
				result[i] = genesB[i];
			}
		}
		
		return result;
	}

	public void setFixedOffsetPercent(float fixedOffsetPercent) {
		this.fixedOffsetPercent = fixedOffsetPercent;
	}

	public void setRandomlySwapParents(boolean randomlySwapParents) {
		this.randomlySwapParents = randomlySwapParents;
	}

	public void setRandomlyChooseOffset(boolean randomlyChooseOffset) {
		this.randomlyChooseOffset = randomlyChooseOffset;
	}
}
