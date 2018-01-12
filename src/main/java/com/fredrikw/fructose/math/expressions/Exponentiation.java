package com.fredrikw.fructose.math.expressions;

public class Exponentiation implements Term {
	private final Term base;
	private final Term exponent;
	
	public Exponentiation(Term base, Term exponent) {
		this.base = base;
		this.exponent = exponent;
	}
	
	@Override
	public double result() {
		return Math.pow(base.result(), exponent.result());
	}
	
	@Override
	public String toString() {
		return base.toString() + " ^ " + exponent.toString();
	}
	
	public Term getBase() {
		return base;
	}

	public Term getExponent() {
		return exponent;
	}
}
