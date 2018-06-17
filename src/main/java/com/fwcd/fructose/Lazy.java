package com.fwcd.fructose;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Stores a lazily loaded reference to an object.
 */
public class Lazy<T> {
	private final Supplier<T> getter;
	private T value;

	private Lazy(T value) {
		getter = null;
		this.value = value;
	}

	public Lazy(Supplier<T> getter) {
		this.getter = getter;
	}

	public static <T> Lazy<T> of(Supplier<T> getter) {
		return new Lazy<>(getter);
	}

	public static <T> Lazy<T> ofConstant(T value) {
		return new Lazy<>(value);
	}

	public <R> Lazy<R> map(Function<? super T, ? extends R> mapper) {
		if (value == null) {
			return of(() -> mapper.apply(value));
		} else {
			return ofConstant(mapper.apply(value));
		}
	}

	public T get() {
		if (value == null) {
			value = getter.get();
		}

		return value;
	}
}
