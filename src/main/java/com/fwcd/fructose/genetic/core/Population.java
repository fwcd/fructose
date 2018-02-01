package com.fwcd.fructose.genetic.core;

import java.util.List;
import java.util.stream.Stream;

import com.fwcd.fructose.genetic.operators.Decoder;

/**
 * A mutable collection of individuals.
 * 
 * @author Fredrik
 *
 * @param <G> - The genotype
 */
public interface Population<G> {
	void setMutationChance(float chance);

	/**
	 * @return The amount of individuals
	 */
	int size();

	int getGeneration();

	G getIndividualGenes(int index);

	void setAllGenes(List<G> individuals);

	List<G> getAllGenes();

	/**
	 * Evolves this population to the next generation. This
	 * includes selection, crossover and mutation.
	 */
	void evolve();

	G getFittestGenes();

	default <P> P getIndividualPhenes(int index, Decoder<G, P> decoder) {
		return decoder.decode(getIndividualGenes(index));
	}

	default Stream<G> streamGenes() {
		return getAllGenes().stream();
	}

	default <P> P getFittestPhenes(Decoder<G, P> decoder) {
		return decoder.decode(getFittestGenes());
	}

	default <P> Stream<P> streamPhenes(Decoder<G, P> decoder) {
		return streamGenes().map(decoder::decode);
	}

	default void evolve(int generations) {
		for (int i=0; i<generations; i++) {
			evolve();
		}
	}
}