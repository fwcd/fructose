package com.fredrikw.fructose.math.expressions;

public class SquareRoot implements Term {
	private final Term radicand;
	
	public SquareRoot(Term radicand) {
		this.radicand = radicand;
	}
	
	@Override
	public double result() {
		return Math.sqrt(radicand.result());
	}

	public Term getRadicand() {
		return radicand;
	}
}
