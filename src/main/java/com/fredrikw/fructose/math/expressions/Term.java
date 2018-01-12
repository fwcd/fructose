package com.fredrikw.fructose.math.expressions;

@FunctionalInterface
public interface Term {
	double result();
	
	default boolean hasResult() {
		return true;
	}
}
