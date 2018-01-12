package com.fredrikw.fructose.math.expressions;

import com.fredrikw.fructose.math.ExtMath;
import com.fredrikw.fructose.math.LongDivision;

public class ConstFraction implements Term {
	private final long counter;
	private final long denominator;
		
	public ConstFraction(long counter, long denominator) {
		this.counter = counter;
		this.denominator = denominator;
	}
	
	public int periodicSequenceLength() {
		return new LongDivision((int) counter, (int) denominator)
				.periodicSequenceLength();
	}
	
	@Override
	public double result() {
		return (double) counter / (double) denominator;
	}
	
	public ConstFraction add(long n) {
		return add(new ConstFraction(n, 1));
	}
	
	public ConstFraction add(ConstFraction other) {
		return multiply(other.denominator)
				.counterPlus(other.multiply(denominator).counter)
				.reduce();
	}
	
	public ConstFraction multiply(long n) {
		return new ConstFraction(counter * n, denominator * n);
	}
	
	public ConstFraction multiply(ConstFraction other) {
		return new ConstFraction(counter * other.counter, denominator * other.denominator);
	}
	
	/**
	 * Reduces the fraction to the smallest possible terms.
	 * 
	 * @return this (making chain operations easier)
	 */
	public ConstFraction reduce() {
		long gcd = ExtMath.greatestCommonDivisor(counter, denominator);
		return new ConstFraction(counter / gcd, denominator / gcd);
	}
	
	/**
	 * Raises this fraction to the power of -1.
	 * (inverts it/switches counter and denominator)
	 * 
	 * @return
	 */
	public ConstFraction invert() {
		return new ConstFraction(denominator, counter);
	}
	
	@Override
	public String toString() {
		return Long.toString(counter) + " / " + Long.toString(denominator) + " = " + Double.toString(result());
	}
	
	/**
	 * Indicated whether this fraction's term is EXACTLY equal to another.<br><br>
	 * 
	 * 1/2 does NOT "equal" 2/4, while<br>
	 * 1/2 does "equal" 1/2.<br><br>
	 * 
	 * If you want to check for result equality instead, use resultEquals().
	 */
	@Override
	public boolean equals(Object other) {
		return other instanceof ConstFraction
				&& counter == ((ConstFraction) other).getCounter()
				&& denominator == ((ConstFraction) other).getDenominator();
	}
	
	public boolean resultEquals(ConstFraction other) {
		return result() == other.result();
	}

	public ConstFraction counterPlus(long v) {
		return new ConstFraction(counter + v, denominator);
	}
	
	public ConstFraction denominatorPlus(long v) {
		return new ConstFraction(counter, denominator + v);
	}
	
	public ConstFraction withCounter(long v) {
		return new ConstFraction(v, denominator);
	}

	public ConstFraction withDenominator(long v) {
		return new ConstFraction(counter, v);
	}
	
	public long getCounter() {
		return counter;
	}

	public long getDenominator() {
		return denominator;
	}

	@Override
	public int hashCode() {
		return 31 * ((int) counter) * ((int) denominator);
	}
}
