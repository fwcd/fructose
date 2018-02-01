package com.fwcd.fructose.genetic.core;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * An object that holds a Population and allows for easy
 * serialization/deserialization.
 * 
 * @author Fredrik
 *
 */
public class PopulationManager<G> {
	private Population<G> population;
	
	public void map(UnaryOperator<Population<G>> mapper) {
		set(mapper.apply(get()));
	}
	
	public void usingPopulation(Consumer<Population<G>> consumer) {
		consumer.accept(get());
	}
	
	public void set(Population<G> population) {
		this.population = population;
	}
	
	public Population<G> get() {
		if (hasPopulation()) {
			return population;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public boolean hasPopulation() {
		return population != null;
	}
	
	public void load(File file) {
		// TODO
	}
	
	public void save(File file) {
		// TODO
	}
}
