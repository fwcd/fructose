package com.fredrikw.fructose.math.expressions;

public class Logarithm implements Term {
	private final Term argument;
	private final Term base;

	public Logarithm(Term argument, Term base) {
		this.argument = argument;
		this.base = base;
	}
	
	@Override
	public double result() {
		return Math.log(argument.result()) / Math.log(base.result());
	}

	public Term getArgument() {
		return argument;
	}

	public Term getBase() {
		return base;
	}
}
