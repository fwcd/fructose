package com.fredrikw.fructose;

import java.util.Iterator;

/**
 * An iterator using the supertype, created from an
 * iterator/iterable of a subtype.
 * 
 * @author Fredrik
 *
 * @param <T> - The supertype
 */
public class SuperIterator<T> implements Iterator<T> {
	private final Iterator<? extends T> subIterator;
	
	public SuperIterator(Iterator<? extends T> iterator) {
		subIterator = iterator;
	}
	
	public SuperIterator(Iterable<? extends T> items) {
		subIterator = items.iterator();
	}

	@Override
	public boolean hasNext() {
		return subIterator.hasNext();
	}

	@Override
	public T next() {
		return subIterator.next();
	}
}
