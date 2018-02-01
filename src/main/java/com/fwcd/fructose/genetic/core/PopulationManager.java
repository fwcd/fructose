package com.fwcd.fructose.genetic.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import com.fwcd.fructose.exception.SerializationException;

/**
 * An object that holds a Population and allows for easy
 * serialization/deserialization.
 * 
 * @author Fredrik
 *
 */
public class PopulationManager {
	private CrossoverPopulation population;
	
	public void map(UnaryOperator<CrossoverPopulation> mapper) {
		set(mapper.apply(get()));
	}
	
	public void usingPopulation(Consumer<CrossoverPopulation> consumer) {
		consumer.accept(get());
	}
	
	public void set(CrossoverPopulation population) {
		this.population = population;
	}
	
	public CrossoverPopulation get() {
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
