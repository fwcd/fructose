package fwcd.fructose.structs;

import java.util.Map;

/**
 * A map that does not only map keys to values, but
 * also maps values back to keys.
 * 
 * @author Fredrik
 *
 * @param <K>
 * @param <V>
 */
public interface BidiMap<K, V> extends Map<K, V> {
	BidiMap<V, K> inverse();
	
	K getKey(Object value);
}