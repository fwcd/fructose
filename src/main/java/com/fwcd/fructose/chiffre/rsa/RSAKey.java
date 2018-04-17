package com.fwcd.fructose.chiffre.rsa;

import java.math.BigInteger;

public class RSAKey {
	private final BigInteger exponent;
	private final BigInteger modulus;
	private final int bits;
	
	public RSAKey(BigInteger exponent, BigInteger modulus, int bits) {
		this.exponent = exponent;
		this.modulus = modulus;
		this.bits = bits;
	}

	public BigInteger getExponent() {
		return exponent;
	}

	public BigInteger getModulus() {
		return modulus;
	}
	
	public int getBits() {
		return bits;
	}
	
	@Override
	public String toString() {
		return "[" + bits + "bit RSA-Key] Exponent: " + exponent + ", Modulus: " + modulus;
	}
}
