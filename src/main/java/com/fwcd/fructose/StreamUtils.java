package com.fwcd.fructose;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.fwcd.fructose.function.FunctionUtils;

public final class StreamUtils {
	private StreamUtils() {}
	
	/**
	 * Zips two streams together using a function.
	 * 
	 * @return A stream containing the zipped elements
	 */
	public static <A, B, R> Stream<R> zip(Stream<? extends A> a, Stream<? extends B> b, BiFunction<? super A, ? super B, ? extends R> zipper) {
		// Source: https://stackoverflow.com/questions/17640754/zipping-streams-using-jdk8-with-lambda-java-util-stream-streams-zip
		// Author: https://stackoverflow.com/users/2163864/siki
		
		Objects.requireNonNull(zipper, "Stream zipper can not be null");
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		
		Spliterator<? extends A> aSpliterator = a.spliterator();
		Spliterator<? extends B> bSpliterator = b.spliterator();
		
		int characteristics = aSpliterator.characteristics()
			& bSpliterator.characteristics()
			& ~(Spliterator.DISTINCT | Spliterator.SORTED);
		long zipSize = ((characteristics & Spliterator.SIZED) != 0)
			? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
			: -1;
		
		Iterator<R> zippedIterator = new Iterator<R>() {
			private final Iterator<A> aIterator = Spliterators.iterator(aSpliterator);
			private final Iterator<B> bIterator = Spliterators.iterator(bSpliterator);
			
			@Override
			public boolean hasNext() { return aIterator.hasNext() && bIterator.hasNext(); }
			
			@Override
			public R next() { return zipper.apply(aIterator.next(), bIterator.next()); }
		};
		
		Spliterator<R> zippedSpliterator = Spliterators.spliterator(zippedIterator, zipSize, characteristics);
		return StreamSupport.stream(zippedSpliterator, a.isParallel() || b.isParallel());
	}
	
	/**
	 * Concatenates multiple streams.
	 * 
	 * <p>This method is logically equivalent to {@code StreamUtils.merge},
	 * but will work for infinite streams too. Note that the stream is
	 * concatenated recursively, which might result in deep call chains
	 * when many streams are used. Therefore {@code StreamUtils.merge}
	 * might provide better performance for finite streams.</p>
	 */
	@SafeVarargs
	public static <T> Stream<T> concat(Stream<T>... streams) {
		return Stream.of(streams)
			.reduce(Stream::concat)
			.orElseGet(Stream::empty);
	}
	
	/**
	 * Concatenates multiple (finite) streams.
	 * 
	 * <p>This method will not work for infinite streams,
	 * for which {@code StreamUtils.concat} should be preferred.</p>
	 */
	@SafeVarargs
	public static <T> Stream<T> merge(Stream<T>... streams) {
		return Stream.of(streams).flatMap(FunctionUtils::<Stream<T>>identity);
	}
}
