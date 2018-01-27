package com.fredrikw.fructose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.fredrikw.fructose.structs.DoubleList;

/**
 * Represents a weighted list of items from
 * which an item may be picked stochastically.
 * 
 * <p>NOTE that this implementation is NOT thread-safe.</p>
 * 
 * @author Fredrik
 *
 * @param <E> - The item type
 */
public class Distribution<E> {
	private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
	private final List<E> items = new ArrayList<>();
	private DoubleList probabilities = new DoubleList();
	private double total = 0;
	
	public Distribution() {
		
	}
	
	public Distribution(Map<? extends E, ? extends Number> distribution) {
		addAll(distribution);
	}
	
	public Distribution(Map<E, Double> distribution, boolean softmaxed) {
		addAll(distribution, softmaxed);
	}
	
	public void applySoftmax() {
		DoubleList exponentiated = probabilities.map(Math::exp);
		double sum = exponentiated.sum();
		exponentiated.mapInPlace(v -> v / sum);
		probabilities = exponentiated;
		total = 1;
	}
	
	public void addAll(Map<? extends E, ? extends Number> distribution) {
		for (E key : distribution.keySet()) {
			add(key, distribution.get(key).doubleValue());
		}
	}
	
	public void addAll(Map<E, Double> distribution, boolean softmaxed) {
		if (softmaxed) {
			Map<E, Double> copy = new HashMap<>(distribution);
			
			double sum = 0;
			Set<Entry<E, Double>> entries = copy.entrySet();
			for (Map.Entry<E, Double> entry : entries) {
				double res = Math.exp(entry.getValue());
				sum += res;
				entry.setValue(res);
			}
			for (Map.Entry<E, Double> entry : entries) {
				double v = entry.getValue();
				if (v == 0D) {
					entry.setValue(v);
				} else {
					entry.setValue(v / sum);
				}
			}
			addAll(copy);
		} else {
			addAll(distribution);
		}
	}
	
	public void add(E item, double probability) {
		if (probability < 0) {
			throw new IllegalArgumentException("Probability can't be smaller than zero: " + Double.toString(probability));
		} else if (!Double.isFinite(probability)) {
			throw new IllegalArgumentException("Probability has to be finite: " + Double.toString(probability));
		}
		
		items.add(item);
		probabilities.add(probability);
		total += probability;
	}
	
	public int pickIndexStochastically() {
		int resultingIndex = -1;
		double random = RANDOM.nextDouble() * total;
		
		for (int i=0; i<items.size(); i++) {
			random -= probabilities.get(i);
			
			if (random <= 0) {
				resultingIndex = i;
				break;
			}
		}
		
		return resultingIndex;
	}
	
	public E pickStochastically() {
		return items.get(pickIndexStochastically());
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		
		int i = 0;
		for (E item : items) {
			s.append(item).append(": ").append(probabilities.get(i)).append(", ");
			i++;
		}
		
		return s.delete(s.length() - 2, s.length()).append("}").toString();
	}
}
