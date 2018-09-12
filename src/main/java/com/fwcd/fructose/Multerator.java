package com.fwcd.fructose;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class acts as an bridge between different
 * iterator and stream types. It can also convert an (otherwise
 * incompatible) iterator of the supertype "? extends T"
 * to "T".
 *
 * @param <T> - The item data type
 */
public class Multerator<T> implements Enumeration<T>, Iterator<T>, Iterable<T> {
	private Either<Iterator<? extends T>, Enumeration<? extends T>> source;
	
	public Multerator(Stream<? extends T> stream) {
		source = Either.ofLeft(stream.iterator());
	}
	
	public Multerator(Enumeration<? extends T> enumeration) {
		source = Either.ofRight(enumeration);
	}
	
	public Multerator(Iterator<? extends T> iterator) {
		source = Either.ofLeft(iterator);
	}
	
	public Multerator(Iterable<? extends T> iterable) {
		source = Either.ofLeft(iterable.iterator());
	}
	
	@Override
	public Iterator<T> iterator() { return this; }

	@Override
	public boolean hasNext() { return source.reduce(Iterator::hasNext, Enumeration::hasMoreElements); }

	@Override
	public T next() { return source.reduce(Iterator::next, Enumeration::nextElement); }
	
	public Stream<T> stream() { return StreamSupport.stream(spliterator(), false); }

	@Override
	public boolean hasMoreElements() { return hasNext(); }

	@Override
	public T nextElement() { return next(); }
}
