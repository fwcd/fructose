package fwcd.fructose.structs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import fwcd.fructose.EventListenerList;
import fwcd.fructose.ReadOnlyListenableValue;
import fwcd.fructose.structs.events.MapModifyEvent;

/**
 * An unordered, read-only key-value map that can be listened to.
 */
public class ReadOnlyObservableMap<K, V> implements Serializable, ReadOnlyListenableValue<Map<K, V>> {
	private static final long serialVersionUID = 7329096016524609221L;
	private transient EventListenerList<Map<K, V>> nullableChangeListeners;
	private transient EventListenerList<MapModifyEvent<K, V>> nullableModifyListeners;
	private Map<K, V> values;
	
	public ReadOnlyObservableMap() { values = new HashMap<>(); }
	
	public ReadOnlyObservableMap(Map<K, V> values) { this.values = values; }
	
	private EventListenerList<Map<K, V>> getChangeListeners() {
		if (nullableChangeListeners == null) {
			nullableChangeListeners = new EventListenerList<>();
		}
		return nullableChangeListeners;
	}
	
	private EventListenerList<MapModifyEvent<K, V>> getModifyListeners() {
		if (nullableModifyListeners == null) {
			nullableModifyListeners = new EventListenerList<>();
		}
		return nullableModifyListeners;
	}
	
	@Override
	public void listen(Consumer<? super Map<K, V>> listener) { getChangeListeners().add(listener); }
	
	@Override
	public void listenAndFire(Consumer<? super Map<K, V>> listener) {
		listen(listener);
		listener.accept(values);
	}
	
	@Override
	public void unlisten(Consumer<? super Map<K, V>> listener) { getChangeListeners().remove(listener); }
	
	public void listenForModifications(Consumer<? super MapModifyEvent<K, V>> listener) { getModifyListeners().add(listener); }
	
	public void listenForModificationsAndFire(Consumer<? super MapModifyEvent<K, V>> listener) {
		listenForModifications(listener);
		listener.accept(new MapModifyEvent<>(values, Collections.emptySet()));
	}
	
	public void unlistenForModifications(Consumer<? super MapModifyEvent<K, V>> listener) { getModifyListeners().remove(listener); }
	
	@Override
	public Map<K, V> get() { return Collections.unmodifiableMap(values); }
	
	public int size() { return values.size(); }
	
	public boolean isEmpty() { return values.isEmpty(); }
	
	public boolean containsKey(Object key) { return values.containsKey(key); }
	
	public boolean containsValue(Object value) { return values.containsValue(value); }
	
	public Set<K> keySet() { return values.keySet(); }
	
	public Collection<V> values() { return values.values(); }
	
	public Set<Map.Entry<K, V>> entrySet() { return values.entrySet(); }
	
	public V get(Object key) { return values.get(key); }
	
	protected void fireChange() {
		getChangeListeners().fire(values);
	}
	
	protected void fireModification(Map<? extends K, ? extends V> added, Set<? extends K> removed) {
		getModifyListeners().fire(new MapModifyEvent<>(added, removed));
	}
	
	// Protected, mutating methods
	
	protected Map<K, V> getSilentlyMutable() { return values; }
	
	protected void set(Map<K, V> newValues) {
		Set<K> prevKeys = values.keySet();
		values = newValues;
		fireChange();
		fireModification(newValues, prevKeys);
	}

	protected V put(K key, V value) {
		V tmp = values.put(key, value);
		fireChange();
		fireModification(Collections.singletonMap(key, value), Collections.emptySet());
		return tmp;
	}

	@SuppressWarnings("unchecked")
	protected V remove(Object key) {
		V tmp = values.remove(key);
		fireChange();
		fireModification(Collections.emptyMap(), Collections.singleton((K) key));
		return tmp;
	}

	protected void putAll(Map<? extends K, ? extends V> m) {
		values.putAll(m);
		fireChange();
		fireModification(m, Collections.emptySet());
	}

	protected void clear() {
		Set<? extends K> prevKeys = new HashSet<>(values.keySet());
		values.clear();
		fireChange();
		fireModification(Collections.emptyMap(), prevKeys);
	}
}
