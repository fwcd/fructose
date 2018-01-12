package com.fredrikw.fructose;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * This class acts as an bridge between different
 * iterators.
 * 
 * @author Fredrik
 *
 * @param <T> - The item data type
 */
public class Multerator<T> implements Enumeration<T>, Iterator<T>, Iterable<T> {
	private Enumeration<T> enumeration = null;
	private Iterator<T> iterator = null;
	
	public Multerator(Enumeration<T> enumeration) {
		this.enumeration = enumeration;
	}
	
	public Multerator(Iterator<T> iterator) {
		this.iterator = iterator;
	}
	
	public Multerator(Iterable<T> iterable) {
		iterator = iterable.iterator();
	}
	
	@Override
	public Iterator<T> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		if (iterator != null) {
			return iterator.hasNext();
		} else if (enumeration != null) {
			return enumeration.hasMoreElements();
		} else {
			throw new RuntimeException("No compatible iterator present!");
		}
	}

	@Override
	public T next() {
		if (iterator != null) {
			return iterator.next();
		} else if (enumeration != null) {
			return enumeration.nextElement();
		} else {
			throw new RuntimeException("No compatible iterator present!");
		}
	}

	@Override
	public boolean hasMoreElements() {
		return hasNext();
	}

	@Override
	public T nextElement() {
		return next();
	}
}
