package com.fwcd.fructose.math;

public class LongFraction {
	private final long counter;
	private final long denominator;
		
	public LongFraction(long counter, long denominator) {
		this.counter = counter;
		this.denominator = denominator;
	}
	
	public int periodicSequenceLength() {
		return new LongDivision((int) counter, (int) denominator)
				.periodicSequenceLength();
	}
	
	public double compute() {
		return (double) counter / (double) denominator;
	}
	
	public LongFraction add(long n) {
		return add(new LongFraction(n, 1));
	}
	
	public LongFraction add(LongFraction other) {
		return multiply(other.denominator)
				.counterPlus(other.multiply(denominator).counter)
				.reduce();
	}
	
	public LongFraction multiply(long n) {
		return new LongFraction(counter * n, denominator * n);
	}
	
	public LongFraction multiply(LongFraction other) {
		return new LongFraction(counter * other.counter, denominator * other.denominator);
	}
	
	/**
	 * Reduces the fraction to the smallest possible terms.
	 * 
	 * @return this (making chain operations easier)
	 */
	public LongFraction reduce() {
		long gcd = ExtMath.greatestCommonDivisor(counter, denominator);
		return new LongFraction(counter / gcd, denominator / gcd);
	}
	
	/**
	 * Raises this fraction to the power of -1.
	 * (inverts it/switches counter and denominator)
	 * 
	 * @return
	 */
	public LongFraction invert() {
		return new LongFraction(denominator, counter);
	}
	
	@Override
	public String toString() {
		return Long.toString(counter) + " / " + Long.toString(denominator) + " = " + Double.toString(compute());
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
		return other instanceof LongFraction
				&& counter == ((LongFraction) other).getCounter()
				&& denominator == ((LongFraction) other).getDenominator();
	}
	
	public boolean resultEquals(LongFraction other) {
		return compute() == other.compute();
	}

	public LongFraction counterPlus(long v) {
		return new LongFraction(counter + v, denominator);
	}
	
	public LongFraction denominatorPlus(long v) {
		return new LongFraction(counter, denominator + v);
	}
	
	public LongFraction withCounter(long v) {
		return new LongFraction(v, denominator);
	}

	public LongFraction withDenominator(long v) {
		return new LongFraction(counter, v);
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
