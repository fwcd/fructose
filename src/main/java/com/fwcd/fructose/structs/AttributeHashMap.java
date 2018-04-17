package com.fwcd.fructose.structs;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class AttributeHashMap implements AttributeMap {
	private final Map<String, Object> data = new HashMap<>();
	
	@Override
	public void put(String name, Object value) {
		data.put(name, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String name, Class<T> type) {
		try {
			if (data.containsKey(name)) {
				return (T) data.get(name);
			} else {
				throw new NoSuchElementException("The attribute " + name + " is not registered!");
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("The attribute " + name + " does not have the type " + type.getName() + "!");
		}
	}
}
