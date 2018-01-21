package com.fredrikw.fructose;

import java.util.function.Supplier;

/**
 * Stores a lazily loaded reference to an object.
 */
public class Lazy<T> {
	private final Supplier<T> getter;
	private T data;
	
	public Lazy(Supplier<T> getter) {
		this.getter = getter;
	}
	
	public T get() {
		if (data == null) {
			data = getter.get();
		}
		
		return data;
	}
}
