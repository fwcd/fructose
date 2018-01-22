package com.fredrikw.fructose;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * This class acts as an bridge between different
 * iterators. It can also convert an (otherwise
 * incompatible) iterator of the supertype "? extends T"
 * to "T".
 * 
 * @author Fredrik
 *
 * @param <T> - The item data type
 */
public class Multerator<T> implements Enumeration<T>, Iterator<T>, Iterable<T> {
	private Enumeration<? extends T> enumeration = null;
	private Iterator<? extends T> iterator = null;
	
	public Multerator(Enumeration<? extends T> enumeration) {
		this.enumeration = enumeration;
	}
	
	public Multerator(Iterator<? extends T> iterator) {
		this.iterator = iterator;
	}
	
	public Multerator(Iterable<? extends T> iterable) {
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
