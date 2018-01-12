package com.fredrikw.fructose.genetic.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;
import java.util.function.UnaryOperator;

import com.fredrikw.fructose.exception.SerializationException;

public class PopulationManager {
	private Population population;
	
	public void map(UnaryOperator<Population> mapper) {
		set(mapper.apply(get()));
	}
	
	public void set(Population population) {
		this.population = population;
	}
	
	public Population get() {
		if (population != null) {
			return population;
		} else {
			throw new NoSuchElementException();
		}
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
