package com.fwcd.fructose.text;

import java.util.Arrays;

public class StringUtils {
	public static String capitalize(String text) {
		return Character.toUpperCase(text.toCharArray()[0]) + text.substring(1).toLowerCase();
	}
	
	/**
	 * Converts ANY object (including arrays)
	 * to a string at the expense of computation time.
	 * 
	 * <p>This method should only be considered,
	 * when it not known whether the object is an array (or not)
	 * at runtime. Otherwise {@code Object.toString()} or
	 * {@code Arrays.toString()} should be used.</p>
	 * 
	 * @param obj - The object to be converted
	 * @return A string representation
	 */
	public static String toString(Object obj) {
		Class<?> clazz = obj.getClass();
		
		if (clazz.isArray()) {
			if (clazz == int[].class) {
				return Arrays.toString((int[]) obj);
			} else if (clazz == double[].class) {
				return Arrays.toString((double[]) obj);
			} else if (clazz == float[].class) {
				return Arrays.toString((float[]) obj);
			} else if (clazz == boolean[].class) {
				return Arrays.toString((boolean[]) obj);
			} else if (clazz == long[].class) {
				return Arrays.toString((long[]) obj);
			} else if (clazz == char[].class) {
				return Arrays.toString((char[]) obj);
			} else if (clazz == byte[].class) {
				return Arrays.toString((byte[]) obj);
			} else if (clazz == short[].class) {
				return Arrays.toString((short[]) obj);
			} else {
				return Arrays.deepToString((Object[]) obj);
			}
		} else {
			return obj.toString();
		}
	}
}
