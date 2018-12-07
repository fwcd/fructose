package fwcd.fructose.structs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A simple bidirectional map (based on HashMap) that is fully conform to
 * the BidiMap interface.
 * 
 * @author Fredrik
 *
 * @param <K>
 * @param <V>
 */
public class BidiHashMap<K, V> implements BidiMap<K, V> {
	private final Map<K,V> map;
    private final Map<V,K> inverse;
    
    public BidiHashMap() {
    	map = new HashMap<>();
    	inverse = new HashMap<>();
    }
    
    private BidiHashMap(Map<K, V> map, Map<V, K> inverse) {
    	this.map = map;
    	this.inverse = inverse;
    }
    
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
		return inverse.get(value);
	}

	@Override
	public V put(K key, V value) {
		V returnedValue = map.put(key, value);
		K prevValueKey = inverse.put(value, key);
		
		if (prevValueKey != null) {
			remove(prevValueKey);
		}
		
		return returnedValue;
	}

	@Override
	public V remove(Object key) {
		V removedValue = map.remove(key);
		inverse.remove(removedValue);
		
		return removedValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
		
		for (K key : m.keySet()) {
			inverse.put(m.get(key), key);
		}
	}

	@Override
	public void clear() {
		map.clear();
		inverse.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public BidiMap<V, K> inverse() {
		return new BidiHashMap<>(new HashMap<>(inverse), new HashMap<>(map));
	}
}
