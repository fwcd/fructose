package com.fwcd.fructose.structs;

import java.util.Collection;
import java.util.Set;

import com.fwcd.fructose.ListenableValue;

public class ObservableSet<T> extends ReadOnlyObservableSet<T> implements Set<T>, ListenableValue<Set<T>> {
	private static final long serialVersionUID = 7332303678759198441L;

	public ObservableSet() {}
	
	public ObservableSet(Set<T> values) { super(values); }
	
	// Publicly exposes mutating methods
	
	@Override
	public void set(Set<T> values) { super.set(values); }
	
	@Override
	public boolean add(T e) { return super.add(e); }

	@Override
	public boolean remove(Object o) { return super.remove(o); }

	@Override
	public boolean addAll(Collection<? extends T> c) { return super.addAll(c); }

	@Override
	public boolean retainAll(Collection<?> c) { return super.retainAll(c); }

	@Override
	public boolean removeAll(Collection<?> c) { return super.removeAll(c); }

	@Override
	public void clear() { super.clear(); }
	
	@Override
	public Set<T> getSilentlyMutable() { return super.getSilentlyMutable(); }
}
