package com.fwcd.fructose;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface BiIterable<A, B> {
	BiIterator<A, B> iterator();
	
	default Spliterator<Pair<A, B>> spliterator() {
		return Spliterators.spliteratorUnknownSize(iterator(), 0);
	}
	
	default Stream<Pair<A, B>> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
	
	default void forEach(BiConsumer<? super A, ? super B> action) {
		BiIterator<A, B> it = iterator();
		while (it.hasNext()) {
			Pair<A, B> entry = it.next();
			action.accept(entry.getA(), entry.getB());
		}
	}
}
