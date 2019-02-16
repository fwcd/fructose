package fwcd.fructose.structs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

import fwcd.fructose.BiIterator;
import fwcd.fructose.Pair;

/**
 * An {@link ArrayList}-based implementation of {@link BiList}.
 */
public class ArrayBiList<L, R> implements BiList<L, R> {
	private final List<L> leftItems = new ArrayList<>();
	private final List<R> rightItems = new ArrayList<>();
	
	@Override
	public void add(L a, R b) {
		leftItems.add(a);
		rightItems.add(b);
	}

	@Override
	public void remove(int i) {
		leftItems.remove(i);
		rightItems.remove(i);
	}

	@Override
	public int size() {
		int aSize = leftItems.size();
		int bSize = rightItems.size();
		
		if (aSize != bSize) {
			throw new IllegalStateException("Both list column need to have the same length!");
		} else {
			return aSize;
		}
	}

	@Override
	public Pair<L, R> get(int i) {
		return new Pair<>(leftItems.get(i), rightItems.get(i));
	}
	
	@Override
	public BiIterator<L, R> iterator() {
		return new ComposedIterator<>(leftItems.iterator(), rightItems.iterator());
	}

	@Override
	public void forEach(BiConsumer<? super L, ? super R> action) {
		Iterator<L> aIt = leftItems.iterator();
		Iterator<R> bIt = rightItems.iterator();
		
		while (aIt.hasNext() && bIt.hasNext()) {
			action.accept(aIt.next(), bIt.next());
		}
	}

	@Override
	public L getLeft(int i) {
		return leftItems.get(i);
	}

	@Override
	public R getRight(int i) {
		return rightItems.get(i);
	}
	
	@Override
	public List<L> getLeftList() {
		return leftItems;
	}
	
	@Override
	public List<R> getRightList() {
		return rightItems;
	}

	@Override
	public boolean contains(L a, R b) {
		return leftItems.contains(a) && rightItems.contains(b);
	}

	@Override
	public boolean containsLeft(L a) {
		return leftItems.contains(a);
	}

	@Override
	public boolean containsRight(R b) {
		return rightItems.contains(b);
	}
	
	@Override
	public void remove(L a) {
		final int index = leftItems.indexOf(a);
		remove(index);
	}

	@Override
	public void remove(L a, R b) {
		Pair<L, R> p = new Pair<>(a, b);
		BiIterator<L, R> it = iterator();
		while (it.hasNext()) {
			if (it.next().equals(p)) {
				it.remove();
			}
		}
	}

	@Override
	public int indexOf(L a) {
		return leftItems.indexOf(a);
	}

	@Override
	public int indexOf(L a, R b) {
		Pair<L, R> p = new Pair<>(a, b);
		int i = 0;
		for (Pair<L, R> item : this) {
			if (item.equals(p)) {
				return i;
			}
			i++;
		}
		
		throw new NoSuchElementException("Pair (" + a.toString() + ", " + b.toString() + ") is not contained by the BiList!");
	}

	@Override
	public void add(int i, L a, R b) {
		leftItems.add(i, a);
		rightItems.add(i, b);
	}

	@Override
	public void remap(L a, R b) {
		rightItems.set(leftItems.indexOf(a), b);
	}

	@Override
	public void clear() {
		leftItems.clear();
		rightItems.clear();
	}
	
	@Override
	public void setLeft(int i, L value) {
		leftItems.set(i, value);
	}
	
	@Override
	public void setRight(int i, R value) {
		rightItems.set(i, value);
	}
}
