package fwcd.fructose.structs;

import java.util.Map;

import fwcd.fructose.ListenableValue;

public class ObservableMap<K, V> extends ReadOnlyObservableMap<K, V> implements Map<K, V>, ListenableValue<Map<K, V>> {
	private static final long serialVersionUID = -2940107135447980982L;

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
	
	@Override
	public Map<K, V> getSilentlyMutable() { return super.getSilentlyMutable(); }
}
