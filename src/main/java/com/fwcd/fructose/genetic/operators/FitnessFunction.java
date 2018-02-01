package com.fwcd.fructose.genetic.operators;

import java.io.Serializable;

/**
 * A domain-specific, genetic operator that evaluates
 * certain genes.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface FitnessFunction<G> extends Serializable {
	/**
	 * Evaluates an individual's genes and returns
	 * a "fitness rating". It might be desirable to
	 * use a {@link Decoder} to decode the genes.
	 * 
	 * @param genes - The chromosome
	 * @return The "fitness"
	 */
	float getFitness(G genes);
}
