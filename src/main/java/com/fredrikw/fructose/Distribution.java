package com.fredrikw.fructose;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.fredrikw.fructose.structs.FloatList;

/**
 * Represents a weighted list of items from
 * which an item may be picked stochastically.
 * 
 * @author Fredrik
 *
 * @param <E> - The item type
 */
public class Distribution<E> {
	private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
	private final List<E> items = new ArrayList<>();
	private final FloatList probabilities = new FloatList();
	private float total = 0;
	
	public void add(E item, float probability) {
		items.add(item);
		probabilities.add(probability);
		total += probability;
	}
	
	public int pickIndexStochastically() {
		// Total "should" always be 1, but may be different due to
		// floating point inaccuracies
		
		int resultingIndex = -1;
		float random = RANDOM.nextFloat() * total;
		
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
}
