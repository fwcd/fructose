package fwcd.fructose.genetic.operators;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GaussianFloatMutator implements Mutator<float[]> {
	private static final long serialVersionUID = 23465873645873645L;
	
	private final float upperBound;
	private final float lowerBound;
	private final float multiplier;
	private final float bias;

	public GaussianFloatMutator() {
		upperBound = Float.POSITIVE_INFINITY;
		lowerBound = Float.NEGATIVE_INFINITY;
		multiplier = 5;
		bias = 0;
	}
	
	public GaussianFloatMutator(float lowerBound, float upperBound, float multiplier, float bias) {
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.multiplier = multiplier;
		this.bias = bias;
	}
	
	private float probability(float[] genes) {
		return 1F / genes.length;
	}
	
	@Override
	public float[] mutate(float[] genes) {
		float[] result = Arrays.copyOf(genes, genes.length);
		mutateInPlace(result);
		return result;
	}

	@Override
	public void mutateInPlace(float[] genes) {
		Random random = ThreadLocalRandom.current();
		float probability = probability(genes);

		for (int i=0; i<genes.length; i++) {
			if (random.nextFloat() < probability) {
				genes[i] = (genes[i] * (float) random.nextGaussian() * multiplier) + bias;
				
				if (genes[i] < lowerBound) {
					genes[i] = lowerBound;
				} else if (genes[i] > upperBound) {
					genes[i] = upperBound;
				}
			}
		}
	}
}
