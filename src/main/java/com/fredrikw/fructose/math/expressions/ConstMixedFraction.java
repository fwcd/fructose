package com.fredrikw.fructose.math.expressions;

public class ConstMixedFraction implements Term {
	private final long summand;
	private final ConstFraction fraction;
	
	public ConstMixedFraction(long summand, long counter, long denominator) {
		this.summand = summand;
		fraction = new ConstFraction(counter, denominator);
	}
	
	public ConstMixedFraction(long summand, long counter, ConstFraction denominator) {
		this.summand = summand;
		fraction = new ConstFraction(counter, 1).multiply(denominator.invert());
	}
	
	/**
	 * Converts this mixed fraction to a fraction.
	 * 
	 * @return The resulting fraction
	 */
	public ConstFraction combine() {
		return fraction.add(summand);
	}

	@Override
	public double result() {
		return combine().result();
	}
}
