package fwcd.fructose.genetic.operators;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class UniformFloatCrossover implements Crossover<float[]> {
	private static final long serialVersionUID = -3045467067945622581L;
	
	private float mixingRatio = 0.5F;
	
	@Override
	public float[] crossover(float[] genesA, float[] genesB) {
		Random random = ThreadLocalRandom.current();
		int length = Math.min(genesA.length, genesB.length);
		float[] result = new float[length];
		
		for (int i=0; i<length; i++) {
			if (random.nextFloat() < mixingRatio) {
				result[i] = genesA[i];
			} else {
				result[i] = genesB[i];
			}
		}
		
		return result;
	}
}
