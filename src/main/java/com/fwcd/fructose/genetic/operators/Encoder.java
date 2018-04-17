package com.fwcd.fructose.genetic.operators;

import java.io.Serializable;

/**
 * A bridge that converts the actual representation of a solution
 * state to an array of numbers (that are used for computation).
 * 
 * @author Fredrik
 *
 * @param <G> - The genotype
 * @param <P> - The (domain-specific) representation of a solution state (phenotype)
 */
public interface Encoder<G, P> extends Serializable {
	/**
	 * Encodes a solution from the phenotype (an actual solution)
	 * to the genotype.
	 * 
	 * @param phenes - The custom state/solution
	 * @return The gene sequence/chromosome
	 */
	G encode(P phenes);
}
