package com.fwcd.fructose.structs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

import com.fwcd.fructose.EventListenerList;
import com.fwcd.fructose.ReadOnlyListenable;

/**
 * An unordered, read-only set that can be listened to.
 */
public class ReadOnlyObservableSet<T> implements Iterable<T>, ReadOnlyListenable<Set<T>> {
	private final EventListenerList<Set<T>> listeners = new EventListenerList<>();
	private Set<T> values;
	
	public ReadOnlyObservableSet() { values = new HashSet<>(); }
	
	public ReadOnlyObservableSet(Set<T> values) { this.values = values; }
	
	@Override
	public void listen(Consumer<Set<T>> listener) { listeners.add(listener); }
	
	@Override
	public void listenAndFire(Consumer<Set<T>> listener) {
		listen(listener);
		listener.accept(values);
	}
	
	@Override
	public void unlisten(Consumer<Set<T>> listener) { listeners.remove(listener); }
	
	@Override
	public Iterator<T> iterator() { return values.iterator(); }

	public Object[] toArray() { return values.toArray(); }

	public <E> E[] toArray(E[] a) { return values.toArray(a); }
	
	public int size() { return values.size(); }

	public boolean isEmpty() { return values.isEmpty(); }

	public boolean contains(Object o) { return values.contains(o); }

	public boolean containsAll(Collection<?> c) { return values.containsAll(c); }
	
	@Override
	public Set<T> get() { return Collections.unmodifiableSet(values); }
	
	// Publicly exposes mutating methods
	
	protected void set(Set<T> values) {
		this.values = values;
		fire();
	}
	
	protected boolean add(T e) {
		boolean success = values.add(e);
		fire();
		return success;
	}
	
	protected boolean remove(Object o) {
		boolean success = values.remove(o);
		fire();
		return success;
	}
	
	protected boolean addAll(Collection<? extends T> c) {
		boolean success = values.addAll(c);
		fire();
		return success;
	}
	
	protected boolean retainAll(Collection<?> c) {
		boolean success = values.retainAll(c);
		fire();
		return success;
	}
	
	protected boolean removeAll(Collection<?> c) {
		boolean success = values.removeAll(c);
		fire();
		return success;
	}
	
	protected void clear() {
		values.clear();
		fire();
	}
	
	protected void fire() { listeners.fire(values); }
}
