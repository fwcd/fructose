package com.fwcd.fructose.structs;

import java.util.Map;

public class ObservableMap<K, V> extends ReadOnlyObservableMap<K, V> implements Map<K, V> {
	public ObservableMap() {}
	
	public ObservableMap(Map<K, V> values) { super(values); }
	
	// Publicly exposes mutating methods
	
	@Override
	public void set(Map<K, V> values) { super.set(values); }

	@Override
	public V put(K key, V value) { return super.put(key, value); }

	@Override
	public V remove(Object key) { return super.remove(key); }

	@Override
	public void putAll(Map<? extends K, ? extends V> m) { super.putAll(m); }

	@Override
	public void clear() { super.clear(); }
}
