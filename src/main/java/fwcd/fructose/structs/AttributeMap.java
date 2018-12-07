package fwcd.fructose.structs;

/**
 * A dynamic, map-like data structure that can
 * hold any type. While it's certainly very
 * convenient for some purposes where maximum
 * generality is desired, it causes much
 * weaker typing and thus should be used
 * judiciously anyway.
 * 
 * @author Fredrik
 *
 */
public interface AttributeMap {
	void put(String name, Object value);
	
	<T> T get(String name, Class<T> type);
}
