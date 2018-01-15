package com.fredrikw.fructose.genetic.operators;

import java.io.Serializable;

/**
 * A bridge that converts the numeric representation of a solution
 * state to the actual representation.
 * 
 * @author Fredrik
 *
 * @param <T> - The (domain-specific) representation of a solution state
 */
public interface Decoder<T> extends Serializable {
	/**
	 * Decodes a solution from the genotype (the computational
	 * solution space) to phenes suitable for the phenotype.
	 * 
	 * @param genes - The genes/chromosome
	 * @return The actual solution/state or "phenes"
	 */
	T decode(int[] genes);
}
