package com.fwcd.fructose;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * A not-null asserted value. Throws an exception
 * IMMEDIATLY upon assignment of null (or access if empty)
 * to prevent nulls from being passed arbitrarily
 * around the program.
 */
public class NonNull<T> implements Serializable {
	private static final long serialVersionUID = -3491679088491580794L;
	private static final NonNull<Void> EMPTY = new NonNull<>(null, "Empty");
	private final T item;
	private final String description;

	private NonNull(T item, String description) {
		this.item = item;
		this.description = description;

		// Intentionally not asserting non-null here
		// to allow "empty" NonNull's to be constructed.
		// This is useful for fields that are not
		// initialized directly, but not accessed
		// before initialization either.
	}

	@SuppressWarnings("unchecked")
	public static <T> NonNull<T> empty() {
		return (NonNull<T>) EMPTY;
	}

	public static <T> NonNull<T> of(T item) {
		return of(item, null);
	}

	public static <T> NonNull<T> of(T item, String description) {
		NonNull<T> instance = new NonNull<>(item, description);
		instance.assertNonNull();
		return instance;
	}

	private void assertNonNull() {
		if (item == null) {
			String msg;
			if (description == null) {
				msg = toString() + " can not contain null!";
			} else {
				msg = toString() + " can not contain null!";
			}
			throw new NoSuchElementException(msg);
		}
	}

	public T get() {
		assertNonNull();
		return item;
	}

	@Override
	public String toString() {
		return "NonNull (" + description + ")";
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		return item.equals(((NonNull<T>) obj).item);
	}

	@Override
	public int hashCode() {
		return item == null ? 0 : item.hashCode();
	}
}
