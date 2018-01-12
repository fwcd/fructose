package com.fredrikw.fructose.properties;

/**
 * A property wrapping an object.
 * May be used to implement bi-directional
 * binds between view and model.
 * 
 * @author Fredrik W.
 *
 * @param <T> The type of the wrapped object
 * @param <B> The type of the bound object
 */
public interface Property<T, B> {
	/**
	 * Sets the wrapped object.
	 * 
	 * @param data - The new value
	 */
	void set(T data);
	
	/**
	 * Fetches the wrapped object
	 * 
	 * @return The wrapped object
	 */
	T get();
	
	/**
	 * Establishes a bi-directional bind
	 * between this property and another object.
	 * 
	 * @param component - The component to which this property is bound
	 */
	void bind(B component);
}
