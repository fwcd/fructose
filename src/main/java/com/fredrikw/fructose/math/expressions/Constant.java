package com.fredrikw.fructose.math.expressions;

/**
 * A constant value.
 * 
 * @author Fredrik
 *
 */
public class Constant implements Term {
	private final double value;
	
	public Constant(double value) {
		this.value = value;
	}
	
	@Override
	public double result() {
		return value;
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}
	
	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}
	
	@Override
	public boolean equals(Object o) {
		return value == ((Constant) o).value;
	}
}
