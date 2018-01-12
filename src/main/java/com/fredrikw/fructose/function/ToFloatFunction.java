package com.fredrikw.fructose.function;

@FunctionalInterface
public interface ToFloatFunction<T> {
	float apply(T input);
}
