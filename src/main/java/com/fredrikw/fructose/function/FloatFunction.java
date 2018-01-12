package com.fredrikw.fructose.function;

@FunctionalInterface
public interface FloatFunction<R> {
	R apply(float value);
}
