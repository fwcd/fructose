package com.fredrikw.fructose.genetic.operators;

import java.io.Serializable;

/**
 * A genetic operator that mutates a gene sequence.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface Mutator extends Serializable {
	/**
	 * Mutates an individual's genes. This function
	 * should not change the input array's state.
	 * 
	 * @param genes - The chromosome/genes
	 * @return A mutated version of the chromosome
	 */
	int[] mutate(int[] genes);
}
