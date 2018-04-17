package com.fwcd.fructose.structs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

import com.fwcd.fructose.BiIterator;
import com.fwcd.fructose.Pair;

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

	@Override
	public A getA(int i) {
		return aItems.get(i);
	}

	@Override
	public B getB(int i) {
		return bItems.get(i);
	}

	@Override
	public boolean contains(A a, B b) {
		return aItems.contains(a) && bItems.contains(b);
	}

	@Override
	public boolean containsA(A a) {
		return aItems.contains(a);
	}

	@Override
	public boolean containsB(B b) {
		return bItems.contains(b);
	}
	
	@Override
	public void remove(A a) {
		final int index = aItems.indexOf(a);
		remove(index);
	}

	@Override
	public void remove(A a, B b) {
		Pair<A, B> p = new Pair<>(a, b);
		BiIterator<A, B> it = iterator();
		while (it.hasNext()) {
			if (it.next().equals(p)) {
				it.remove();
			}
		}
	}

	@Override
	public int indexOf(A a) {
		return aItems.indexOf(a);
	}

	@Override
	public int indexOf(A a, B b) {
		Pair<A, B> p = new Pair<>(a, b);
		int i = 0;
		for (Pair<A, B> item : this) {
			if (item.equals(p)) {
				return i;
			}
			i++;
		}
		
		throw new NoSuchElementException("Pair (" + a.toString() + ", " + b.toString() + ") is not contained by the BiList!");
	}

	@Override
	public void add(int i, A a, B b) {
		aItems.add(i, a);
		bItems.add(i, b);
	}

	@Override
	public void remap(A a, B b) {
		bItems.set(aItems.indexOf(a), b);
	}

	@Override
	public void clear() {
		aItems.clear();
		bItems.clear();
	}
}
