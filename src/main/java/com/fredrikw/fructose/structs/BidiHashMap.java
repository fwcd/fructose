package com.fredrikw.fructose.structs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A simple bidirectional map (based on HashMap) that is fully conform to
 * the BidiMap interface.
 * 
 * @author Fredrik W.
 *
 * @param <K>
 * @param <V>
 */
public class BidiHashMap<K, V> implements BidiMap<K, V> {
	private HashMap<K,V> map = new HashMap<K, V>();
    private HashMap<V,K> inversedMap = new HashMap<V, K>();

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}
	
	@Override
	public K getKey(Object value) {
		return inversedMap.get(value);
	}

	@Override
	public V put(K key, V value) {
		V returnedValue = map.put(key, value);
		inversedMap.put(value, key);
		
		return returnedValue;
	}

	@Override
	public V remove(Object key) {
		V removedValue = map.remove(key);
		inversedMap.remove(removedValue);
		
		return removedValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
		
		for (K key : m.keySet()) {
			inversedMap.put(m.get(key), key);
		}
	}

	@Override
	public void clear() {
		map.clear();
		inversedMap.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}
}
