package com.fredrikw.fructose.math.expressions;

import java.util.NoSuchElementException;
import java.util.OptionalDouble;

public class Variable implements Term {
	private final String name;
	private OptionalDouble value = OptionalDouble.empty();
	
	public Variable(String name) {
		this.name = name;
	}
	
	public void setValue(double value) {
		this.value = OptionalDouble.of(value);
	}
	
	public OptionalDouble getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public double result() {
		return value.orElseThrow(NoSuchElementException::new);
	}
	
	@Override
	public boolean hasResult() {
		return value.isPresent();
	}
}
