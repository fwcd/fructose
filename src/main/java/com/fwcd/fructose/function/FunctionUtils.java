package com.fwcd.fructose.function;

public final class FunctionUtils {
	private FunctionUtils() {}
	
	public static <T> T identity(T value) {
		return value;
	}
}
