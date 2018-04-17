package com.fwcd.fructose.genetic.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Supplier;
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

	G getGenes(int index);

	void clear();
	
	void addGenes(G genes);
	
	void setAllGenes(List<G> individuals);

	List<G> getAllGenes();

	/**
	 * Evolves this population to the next generation. This
	 * includes selection, crossover and mutation.
	 */
	void evolve();

	/**
	 * Selects the "best" genes in this population. A greedy
	 * approach might be to take the fittest value.
	 * 
	 * @return The "best" genes in this population
	 */
	G selectBestGenes();
	
	void saveTo(OutputStream out);
	
	void loadFrom(InputStream in);
	
	default void spawn(int individuals, Supplier<G> spawner) {
		for (int i=0; i<individuals; i++) {
			addGenes(spawner.get());
		}
	}
	
	default <P> P getPhenes(int index, Decoder<G, P> decoder) {
		return decoder.decode(getGenes(index));
	}

	default Stream<G> streamGenes() {
		return getAllGenes().stream();
	}

	default <P> P selectFittestPhenes(Decoder<G, P> decoder) {
		return decoder.decode(selectBestGenes());
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