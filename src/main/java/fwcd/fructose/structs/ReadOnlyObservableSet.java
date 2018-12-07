package fwcd.fructose.structs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import fwcd.fructose.EventListenerList;
import fwcd.fructose.ReadOnlyListenableValue;
import fwcd.fructose.StreamUtils;
import fwcd.fructose.structs.events.SetModifyEvent;

/**
 * An unordered, read-only set that can be listened to.
 */
public class ReadOnlyObservableSet<T> implements Serializable, Iterable<T>, ReadOnlyListenableValue<Set<T>> {
	private static final long serialVersionUID = -1863477769994694922L;
	private transient EventListenerList<Set<T>> nullableChangeListeners;
	private transient EventListenerList<SetModifyEvent<T>> nullableModifyListeners;
	private Set<T> values;
	
	public ReadOnlyObservableSet() { values = new HashSet<>(); }
	
	public ReadOnlyObservableSet(Set<T> values) { this.values = values; }
	
	private EventListenerList<Set<T>> getChangeListeners() {
		if (nullableChangeListeners == null) {
			nullableChangeListeners = new EventListenerList<>();
		}
		return nullableChangeListeners;
	}
	
	private EventListenerList<SetModifyEvent<T>> getModifyListeners() {
		if (nullableModifyListeners == null) {
			nullableModifyListeners = new EventListenerList<>();
		}
		return nullableModifyListeners;
	}
	
	@Override
	public void listen(Consumer<? super Set<T>> listener) { getChangeListeners().add(listener); }
	
	@Override
	public void listenAndFire(Consumer<? super Set<T>> listener) {
		listen(listener);
		listener.accept(values);
	}
	
	@Override
	public void unlisten(Consumer<? super Set<T>> listener) { getChangeListeners().remove(listener); }
	
	public void listenForModifications(Consumer<? super SetModifyEvent<T>> listener) { getModifyListeners().add(listener); }
	
	public void listenForModificationsAndFire(Consumer<? super SetModifyEvent<T>> listener) {
		listenForModifications(listener);
		listener.accept(new SetModifyEvent<>(values, Collections.emptySet()));
	}
	
	public void unlistenForModifications(Consumer<? super SetModifyEvent<T>> listener) { getModifyListeners().remove(listener); }
	
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
		getChangeListeners().fire(values);
	}
	
	protected void fireModification(Set<? extends T> added, Set<? extends T> removed) {
		getModifyListeners().fire(new SetModifyEvent<>(added, removed));
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
