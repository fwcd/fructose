package com.fredrikw.fructose.math.expressions;

/**
 * The natural logarithm (base e).
 * 
 * @author Fredrik
 *
 */
public class Ln implements Term {
	private final double number;
	
	public Ln(double number) {
		this.number = number;
	}
	
	@Override
	public double result() {
		return Math.log(number);
	}

	public double getNumber() {
		return number;
	}
}
