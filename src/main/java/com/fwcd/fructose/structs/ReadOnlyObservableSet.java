package com.fwcd.fructose.structs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.fwcd.fructose.EventListenerList;
import com.fwcd.fructose.ReadOnlyListenableValue;
import com.fwcd.fructose.StreamUtils;
import com.fwcd.fructose.structs.events.SetModifyEvent;

/**
 * An unordered, read-only set that can be listened to.
 */
public class ReadOnlyObservableSet<T> implements Iterable<T>, ReadOnlyListenableValue<Set<T>> {
	private final EventListenerList<Set<T>> changeListeners = new EventListenerList<>();
	private final EventListenerList<SetModifyEvent<T>> modifyListeners = new EventListenerList<>();
	private Set<T> values;
	
	public ReadOnlyObservableSet() { values = new HashSet<>(); }
	
	public ReadOnlyObservableSet(Set<T> values) { this.values = values; }
	
	@Override
	public void listen(Consumer<? super Set<T>> listener) { changeListeners.add(listener); }
	
	@Override
	public void listenAndFire(Consumer<? super Set<T>> listener) {
		listen(listener);
		listener.accept(values);
	}
	
	@Override
	public void unlisten(Consumer<? super Set<T>> listener) { changeListeners.remove(listener); }
	
	public void listenForModifications(Consumer<? super SetModifyEvent<T>> listener) { modifyListeners.add(listener); }
	
	public void listenForModificationsAndFire(Consumer<? super SetModifyEvent<T>> listener) {
		listenForModifications(listener);
		listener.accept(new SetModifyEvent<>(values, Collections.emptySet()));
	}
	
	public void unlistenForModifications(Consumer<? super SetModifyEvent<T>> listener) { modifyListeners.remove(listener); }
	
	public Stream<T> stream() { return StreamUtils.stream(this); }
	
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
	
	protected void fireChange() {
		changeListeners.fire(values);
	}
	
	protected void fireModification(Set<? extends T> added, Set<? extends T> removed) {
		modifyListeners.fire(new SetModifyEvent<>(added, removed));
	}
	
	// Protected, mutating methods
	
	protected Set<T> getSilentlyMutable() { return values; }
	
	protected void set(Set<T> newValues) {
		Set<T> prevValues = values;
		values = newValues;
		fireChange();
		fireModification(newValues, prevValues);
	}
	
	protected boolean add(T e) {
		boolean success = values.add(e);
		fireChange();
		fireModification(Collections.singleton(e), Collections.emptySet());
		return success;
	}
	
	@SuppressWarnings("unchecked")
	protected boolean remove(Object o) {
		boolean success = values.remove(o);
		fireChange();
		fireModification(Collections.emptySet(), Collections.singleton((T) o));
		return success;
	}
	
	protected boolean addAll(Collection<? extends T> c) {
		Set<? extends T> added = new HashSet<>(c);
		boolean success = values.addAll(added);
		fireChange();
		fireModification(added, Collections.emptySet());
		return success;
	}
	
	protected boolean retainAll(Collection<?> c) {
		Set<T> prevValues = new HashSet<>(values);
		boolean success = values.retainAll(c);
		fireChange();
		fireModification(values, prevValues);
		return success;
	}
	
	@SuppressWarnings("unchecked")
	protected boolean removeAll(Collection<?> c) {
		Set<? extends T> removed = new HashSet<>((Collection<? extends T>) c);
		boolean success = values.removeAll(removed);
		fireChange();
		fireModification(Collections.emptySet(), removed);
		return success;
	}
	
	protected void clear() {
		Set<T> prevValues = new HashSet<>(values);
		values.clear();
		fireChange();
		fireModification(Collections.emptySet(), prevValues);
	}
}
