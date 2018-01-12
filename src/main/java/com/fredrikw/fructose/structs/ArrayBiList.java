package com.fredrikw.fructose.structs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

import com.fredrikw.fructose.BiIterator;
import com.fredrikw.fructose.Pair;

public class ArrayBiList<A, B> implements BiList<A, B> {
	private final List<A> aItems = new ArrayList<>();
	private final List<B> bItems = new ArrayList<>();
	
	@Override
	public void add(A a, B b) {
		aItems.add(a);
		bItems.add(b);
	}

	@Override
	public void remove(int i) {
		aItems.remove(i);
		bItems.remove(i);
	}

	@Override
	public int size() {
		int aSize = aItems.size();
		int bSize = bItems.size();
		
		if (aSize != bSize) {
			throw new IllegalStateException("Both list column need to have the same length!");
		} else {
			return aSize;
		}
	}

	@Override
	public Pair<A, B> get(int i) {
		return new Pair<>(aItems.get(i), bItems.get(i));
	}
	
	@Override
	public BiIterator<A, B> iterator() {
		return new ComposedIterator<>(aItems.iterator(), bItems.iterator());
	}

	@Override
	public void forEach(BiConsumer<? super A, ? super B> action) {
		Iterator<A> aIt = aItems.iterator();
		Iterator<B> bIt = bItems.iterator();
		
		while (aIt.hasNext() && bIt.hasNext()) {
			action.accept(aIt.next(), bIt.next());
		}
	}
}
