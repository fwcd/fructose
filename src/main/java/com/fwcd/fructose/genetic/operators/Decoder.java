package com.fwcd.fructose.genetic.operators;

import java.io.Serializable;

/**
 * A bridge that converts the numeric representation of a solution
 * state to the actual representation.
 * 
 * @author Fredrik
 *
 * @param <G> - The genotype
 * @param <P> - The (domain-specific) representation of a solution state (phenotype)
 */
public interface Decoder<G, P> extends Serializable {
	/**
	 * Decodes a solution from the genotype (the numeric representation of a solution)
	 * to the phenotype.
	 * 
	 * @param genes - The genotype
	 * @return The actual solution/state or "phenes"
	 */
	P decode(G genotype);
}
