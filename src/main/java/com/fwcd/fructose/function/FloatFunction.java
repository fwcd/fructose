package com.fwcd.fructose.function;

@FunctionalInterface
public interface FloatFunction<R> {
	R apply(float value);
}
