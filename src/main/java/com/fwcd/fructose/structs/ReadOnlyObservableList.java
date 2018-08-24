package com.fwcd.fructose.structs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import com.fwcd.fructose.EventListenerList;
import com.fwcd.fructose.ReadOnlyListenable;

/**
 * A read-only list that can be listened to.
 */
public class ReadOnlyObservableList<T> implements Iterable<T>, ReadOnlyListenable<List<T>> {
	private final EventListenerList<List<T>> listeners = new EventListenerList<>();
	private List<T> values;
	
	public ReadOnlyObservableList() { values = new ArrayList<>(); }
	
	public ReadOnlyObservableList(List<T> values) { this.values = values; }
	
	@Override
	public void listen(Consumer<List<T>> listener) { listeners.add(listener); }
	
	@Override
	public void listenAndFire(Consumer<List<T>> listener) {
		listen(listener);
		listener.accept(values);
	}
	
	@Override
	public void unlisten(Consumer<List<T>> listener) { listeners.remove(listener); }
	
	@Override
	public List<T> get() { return Collections.unmodifiableList(values); }
	
	public T get(int index) { return values.get(index); }
	
	public int size() { return values.size(); }
	
	public boolean isEmpty() { return values.isEmpty(); }
	
	public boolean contains(Object o) { return values.contains(o); }
	
	@Override
	public Iterator<T> iterator() { return values.iterator(); }
	
	public Object[] toArray() { return values.toArray(); }
	
	public <E> E[] toArray(E[] a) { return values.toArray(a); }
	
	public boolean containsAll(Collection<?> c) { return values.containsAll(c); }

	public int indexOf(Object o) { return values.indexOf(o); }

	public int lastIndexOf(Object o) { return values.lastIndexOf(o); }

	public ListIterator<T> listIterator() { return values.listIterator(); }

	public ListIterator<T> listIterator(int index) { return values.listIterator(index); }

	public List<T> subList(int fromIndex, int toIndex) { return values.subList(fromIndex, toIndex); }
	
	// Protected, mutating methods
	
	protected boolean add(T value) {
		boolean success = values.add(value);
		fire();
		return success;
	}
	
	protected void set(List<T> values) {
		this.values = values;
		fire();
	}
	
	protected boolean remove(Object value) {
		boolean success = values.remove(value);
		fire();
		return success;
	}
	
	protected T remove(int index) {
		T removed = values.remove(index);
		fire();
		return removed;
	}
	
	protected void add(int index, T value) {
		values.add(index, value);
		fire();
	}
	
	protected T set(int index, T value) {
		T removed = values.set(index, value);
		fire();
		return removed;
	}
	
	protected void use(Consumer<List<T>> user) {
		user.accept(values);
		fire();
	}
	
	protected void fire() {
		listeners.fire(Collections.unmodifiableList(values));
	}
	
	protected boolean addAll(Collection<? extends T> c) {
		boolean success = values.addAll(c);
		fire();
		return success;
	}
	
	protected boolean addAll(int index, Collection<? extends T> c) {
		boolean success = values.addAll(index, c);
		fire();
		return success;
	}

	protected boolean removeAll(Collection<?> c) {
		boolean success = values.removeAll(c);
		fire();
		return success;
	}

	protected boolean retainAll(Collection<?> c) {
		boolean success = values.retainAll(c);
		fire();
		return success;
	}
	
	protected void clear() {
		values.clear();
		fire();
	}
}
