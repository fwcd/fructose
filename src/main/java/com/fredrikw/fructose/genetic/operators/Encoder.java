package com.fredrikw.fructose.genetic.operators;

import java.io.Serializable;

/**
 * A bridge that converts the actual representation of a solution
 * state to an array of numbers (that are used for computation).
 * 
 * @author Fredrik
 *
 * @param <T> - The (domain-specific) representation of a solution state
 */
public interface Encoder<T> extends Serializable {
	/**
	 * Encodes a solution from the phenotype (the actual
	 * solution space) to genes suitable for the genotype.
	 * 
	 * @param phenes - The custom state/solution
	 * @return The gene sequence/chromosome
	 */
	int[] encode(T phenes);
}
