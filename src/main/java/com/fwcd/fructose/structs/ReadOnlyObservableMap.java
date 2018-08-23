package com.fwcd.fructose.structs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.fwcd.fructose.EventListenerList;
import com.fwcd.fructose.Listenable;

public class ReadOnlyObservableMap<K, V> implements Listenable<Map<K, V>> {
	private final EventListenerList<Map<K, V>> listeners = new EventListenerList<>();
	private Map<K, V> values;
	
	public ReadOnlyObservableMap() { values = new HashMap<>(); }
	
	public ReadOnlyObservableMap(Map<K, V> values) { this.values = values; }
	
	@Override
	public void listen(Consumer<Map<K, V>> listener) { listeners.add(listener); }
	
	@Override
	public void listenAndFire(Consumer<Map<K, V>> listener) {
		listen(listener);
		listener.accept(values);
	}
	
	@Override
	public void unlisten(Consumer<Map<K, V>> listener) { listeners.remove(listener); }
	
	public Map<K, V> get() { return Collections.unmodifiableMap(values); }
	
	public int size() { return values.size(); }
	
	public boolean isEmpty() { return values.isEmpty(); }
	
	public boolean containsKey(Object key) { return values.containsKey(key); }
	
	public boolean containsValue(Object value) { return values.containsValue(value); }
	
	public Set<K> keySet() { return values.keySet(); }
	
	public Collection<V> values() { return values.values(); }
	
	public Set<Map.Entry<K, V>> entrySet() { return values.entrySet(); }
	
	public V get(Object key) { return values.get(key); }
	
	// Protected, mutating methods
	
	protected void set(Map<K, V> values) {
		this.values = values;
		fire();
	}
	
	protected void fire() { listeners.fire(values); }

	protected V put(K key, V value) {
		V tmp = values.put(key, value);
		fire();
		return tmp;
	}

	protected V remove(Object key) {
		V tmp = values.remove(key);
		fire();
		return tmp;
	}

	protected void putAll(Map<? extends K, ? extends V> m) {
		values.putAll(m);
		fire();
	}

	protected void clear() {
		values.clear();
		fire();
	}
}
