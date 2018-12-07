package fwcd.fructose.genetic.operators;

import java.io.Serializable;

/**
 * A genetic operator that specificies how to individual's
 * genes should be combined to form a child.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface Crossover<G> extends Serializable {
	/**
	 * "Cross-breeds" two individual's chromosomes. This function
	 * should NOT return the same result every time but preferrably
	 * incorporate some kind of randomness. Furthermore the input
	 * arrays should not be modified.
	 * 
	 * @param genesA - The first chromosome/genes
	 * @param genesB - The second chromosome
	 * @return The offspring's genes
	 */
	G crossover(G genesA, G genesB);
}
