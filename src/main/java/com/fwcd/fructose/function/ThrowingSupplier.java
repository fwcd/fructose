package com.fwcd.fructose.function;

public interface ThrowingSupplier<T, E extends Throwable> {
	T get() throws E;
}
