package com.fredrikw.fructose.math.expressions;

@FunctionalInterface
public interface MathFunction {
	double calculate(double... args);
	
	default MathFunction derivative() {
		throw new UnsupportedOperationException();
	}
	
	default MathFunction integral() {
		throw new UnsupportedOperationException();
	}
}
