package com.fredrikw.fructose.math.expressions;

import java.math.BigInteger;

public class BigMixedFraction {
	private BigInteger summand;
	private MutableBigFraction fraction;
	
	public BigMixedFraction(BigInteger summand, BigInteger counter, BigInteger denominator) {
		this.summand = summand;
		fraction = new MutableBigFraction(counter, denominator);
	}
	
	public BigMixedFraction(BigInteger summand, BigInteger counter, MutableBigFraction denominator) {
		this.summand = summand;
		fraction = new MutableBigFraction(counter, BigInteger.ONE).multiply(denominator.invert());
	}
	
	/**
	 * Converts this mixed fraction to a fraction.
	 * 
	 * @return The resulting fraction
	 */
	public MutableBigFraction combine() {
		return fraction.clone().add(summand);
	}
}
