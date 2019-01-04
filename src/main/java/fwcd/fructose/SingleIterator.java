package fwcd.fructose;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator for a single, nullable element.
 * 
 * @param <T> - The element type
 */
public class SingleIterator<T> implements Iterator<T> {
	private final T value;
	private boolean iterated = false;
	
	/**
	 * Creates a new {@link SingleIterator} using the provided value.
	 * 
	 * @param value - The value to iterate over
	 */
	public SingleIterator(T value) {
		this.value = value;
	}
	
	@Override
	public boolean hasNext() {
		return iterated;
	}
	
	@Override
	public T next() {
		if (iterated) {
			throw new NoSuchElementException("Has already returned the element");
		} else {
			iterated = true;
			return value;
		}
	}
}
