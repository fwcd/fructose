package com.fwcd.fructose.structs;

import java.util.Iterator;

import com.fwcd.fructose.BiIterator;
import com.fwcd.fructose.Pair;

public class ComposedIterator<A, B> implements BiIterator<A, B> {
	private final Iterator<A> a;
	private final Iterator<B> b;

	public ComposedIterator(Iterator<A> a, Iterator<B> b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public boolean hasNext() {
		boolean aHasNext = a.hasNext();
		boolean bHasNext = b.hasNext();
		
		if (aHasNext != bHasNext) {
			throw new IllegalStateException("Both iterators need to have the exact same sequence length!");
		} else {
			return aHasNext;
		}
	}
	
	@Override
	public Pair<A, B> next() {
		return new Pair<>(a.next(), b.next());
	}

	@Override
	public void remove() {
		a.remove();
		b.remove();
	}
}
