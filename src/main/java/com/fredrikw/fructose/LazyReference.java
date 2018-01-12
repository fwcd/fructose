package com.fredrikw.fructose;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.fredrikw.fructose.exception.Rethrow;

/**
 * Stores a lazily loaded reference to a new object.<br><br>
 * 
 * Note, however, that this class will NOT work
 * properly with serialization.
 */
public class LazyReference<T> {
	private T data;
	
	private Constructor<? extends T> constructor;
	private Object[] args;
	
	private Class<? extends T> clazz;
	
	/**
	 * Creates a lazy reference to a new object using the
	 * specified constructor and the specified arguments.
	 * 
	 * @param constructor - The constructor
	 * @param args - The constructor arguments
	 */
	public LazyReference(Constructor<? extends T> constructor, Object... args) {
		this.constructor = constructor;
		this.args = args;
	}
	
	/**
	 * Creates a lazy reference to a new object, presuming
	 * there is a no-argument-constructor in the
	 * desired class.
	 * 
	 * @param clazz - The class of the lazily loaded object
	 */
	public LazyReference(Class<? extends T> clazz) {
		this.clazz = clazz;
	}
	
	public T get() {
		if (data == null) {
			try {
				if (constructor != null) {
					data = constructor.newInstance(args);
				} else {
					data = clazz.newInstance();
				}
			} catch (InstantiationException
					| IllegalAccessException
					| IllegalArgumentException
					| InvocationTargetException e) {
				throw new Rethrow(e);
			}
		}
		
		return data;
	}
}
