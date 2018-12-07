package fwcd.fructose.structs;

import java.util.Collection;
import java.util.List;

import fwcd.fructose.ListenableValue;

public class ObservableList<T> extends ReadOnlyObservableList<T> implements List<T>, ListenableValue<List<T>> {
	private static final long serialVersionUID = -3704312724462936191L;

	public ObservableList() {}
	
	public ObservableList(List<T> values) { super(values); }
	
	// Publicly exposes mutating methods
	
	@Override
	public boolean add(T e) { return super.add(e); }
	
	@Override
	public void set(List<T> values) { super.set(values); }

	@Override
	public boolean remove(Object o) { return super.remove(o); }

	@Override
	public boolean addAll(Collection<? extends T> c) { return super.addAll(c); }

	@Override
	public boolean addAll(int index, Collection<? extends T> c) { return super.addAll(c); }

	@Override
	public boolean removeAll(Collection<?> c) { return super.removeAll(c); }

	@Override
	public boolean retainAll(Collection<?> c) { return super.retainAll(c); }

	@Override
	public void clear() { super.clear(); }

	@Override
	public T set(int index, T element) { return super.set(index, element); }

	@Override
	public void add(int index, T element) { super.add(index, element); }

	@Override
	public T remove(int index) { return super.remove(index); }
	
	@Override
	public List<T> getSilentlyMutable() { return super.getSilentlyMutable(); }
}
