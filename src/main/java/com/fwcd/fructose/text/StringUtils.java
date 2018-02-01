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
		if (obj instanceof int[]) {
			return Arrays.toString((int[]) obj);
		} else if (obj instanceof double[]) {
			return Arrays.toString((double[]) obj);
		} else if (obj instanceof float[]) {
			return Arrays.toString((float[]) obj);
		} else if (obj instanceof boolean[]) {
			return Arrays.toString((boolean[]) obj);
		} else if (obj instanceof long[]) {
			return Arrays.toString((long[]) obj);
		} else if (obj instanceof char[]) {
			return Arrays.toString((char[]) obj);
		} else if (obj instanceof byte[]) {
			return Arrays.toString((byte[]) obj);
		} else if (obj instanceof short[]) {
			return Arrays.toString((short[]) obj);
		} else if (obj instanceof Object[]) {
			return Arrays.deepToString((Object[]) obj);
		} else {
			return obj.toString();
		}
	}
}
