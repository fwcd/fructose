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
	private Population population;
	
	public void map(UnaryOperator<Population> mapper) {
		set(mapper.apply(get()));
	}
	
	public void usingPopulation(Consumer<Population> consumer) {
		consumer.accept(get());
	}
	
	public void set(Population population) {
		this.population = population;
	}
	
	public Population get() {
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
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
			population = (Population) in.readObject();
		} catch (ClassNotFoundException | ClassCastException | IOException e) {
			throw new SerializationException(e);
		}
	}
	
	public void save(File file) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(population);
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}
}
