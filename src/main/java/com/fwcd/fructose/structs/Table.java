package com.fwcd.fructose.structs;

import java.util.Collection;

/**
 * A collection that maps pairs of keys to values.
 * 
 * <ul>
 * <li>a, b -> 3</li>
 * <li>b, c -> 4</li>
 * </ul>
 * 
 * @author Fredrik
 *
 * @param <K> - The first key type
 * @param <S> - The second key type
 * @param <V> - The value type
 */
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
