package com.fwcd.fructose;

import java.util.Iterator;

/**
 * An infinitely looping iterator.
 * 
 * @author Fredrik
 *
 * @param <T> - The data type
 */
public class LoopingIterator<T> implements Iterator<T> {
	private final Iterable<T> iterable;
	private Iterator<T> current;
	
	public LoopingIterator(Iterable<T> iterable) {
		this.iterable = iterable;
		current = iterable.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return current.hasNext();
	}

	@Override
	public T next() {
		T next = current.next();
		if (!current.hasNext()) {
			current = iterable.iterator();
		}
		return next;
	}
}
