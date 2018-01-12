package com.fredrikw.fructose.math.expressions;

import java.math.BigInteger;

import com.fredrikw.fructose.math.ExtMath;

public class MutableBigFraction implements Cloneable, CustomTerm<BigInteger> {
	private BigInteger counter;
	private BigInteger denominator;
		
	public MutableBigFraction(BigInteger counter, BigInteger denominator) {
		this.counter = counter;
		this.denominator = denominator;
	}
	
	@Override
	public BigInteger result() {
		return counter.divide(denominator);
	}
	
	public MutableBigFraction add(BigInteger n) {
		return add(new MutableBigFraction(n, BigInteger.ONE));
	}
	
	public MutableBigFraction add(MutableBigFraction other) {
		MutableBigFraction otherClone = other.clone();
		
		otherClone.multiply(denominator);
		multiply(other.denominator);
		counter = counter.add(otherClone.counter);
		
		reduce();
		
		return this;
	}
	
	public MutableBigFraction multiply(BigInteger n) {
		counter = counter.multiply(n);
		denominator = denominator.multiply(n);
		
		return this;
	}
	
	public MutableBigFraction multiply(MutableBigFraction other) {
		counter = counter.multiply(other.getCounter());
		denominator = denominator.multiply(other.getDenominator());
		
		return this;
	}
	
	/**
	 * Reduces the fraction to the smallest possible terms.
	 * 
	 * @return this (making chain operations easier)
	 */
	public MutableBigFraction reduce() {
		BigInteger gcd = ExtMath.greatestCommonDivisor(counter, denominator);
		
		counter = counter.divide(gcd);
		denominator = denominator.divide(gcd);
		
		return this;
	}
	
	/**
	 * Raises this fraction to the power of -1.
	 * (inverts it/switches counter and denominator)
	 * 
	 * @return
	 */
	public MutableBigFraction invert() {
		BigInteger oldDenom = denominator;
		
		denominator = counter;
		counter = oldDenom;
		
		return this;
	}
	
	@Override
	public String toString() {
		return counter.toString() + " / " + denominator.toString() + " = " + result().toString();
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
		return other instanceof MutableBigFraction
				&& counter.equals(((MutableBigFraction) other).getCounter())
				&& denominator.equals(((MutableBigFraction) other).getDenominator());
	}
	
	public boolean resultEquals(MutableBigFraction other) {
		return result().equals(other.result());
	}
	
	@Override
	public MutableBigFraction clone() {
		return new MutableBigFraction(counter, denominator);
	}

	public BigInteger getCounter() {
		return counter;
	}

	public void setCounter(BigInteger counter) {
		this.counter = counter;
	}

	public BigInteger getDenominator() {
		return denominator;
	}

	public void setDenominator(BigInteger denominator) {
		this.denominator = denominator;
	}

	@Override
	public int hashCode() {
		return 31 * counter.hashCode() * denominator.hashCode();
	}
}
