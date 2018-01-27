package com.fredrikw.fructose.structs;

import java.util.Collection;

public interface Table<K, S, V> {
	V put(K key1, S key2, V value);
	
	V remove(K key1, S key2);
	
	V get(K key1, S key2);
	
	BiList<K, S> keySet();
	
	Collection<V> values();
	
	int size();
	
	default V getOrDefault(K key1, S key2, V def) {
		V result = get(key1, key2);
		return result == null ? def : result;
	}
}
