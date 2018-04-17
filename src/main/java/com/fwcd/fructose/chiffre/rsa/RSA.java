package com.fwcd.fructose.chiffre.rsa;

import java.math.BigInteger;

import com.fwcd.fructose.chiffre.AsymmetricEncryption;

/**
 * A pure-Java RSA implementation.
 */
public class RSA implements AsymmetricEncryption<RSAKey, RSAKey> {
	@Override
	public byte[] encrypt(byte[] message, RSAKey publicKey) {
		BigInteger e = publicKey.getExponent();
		BigInteger n = publicKey.getModulus();
		BigInteger m = new BigInteger(message);
		
		if (m.bitCount() > n.bitCount()) {
			// TODO: Split large messages into smaller ones instead of throwing an exception
			throw new IllegalArgumentException("Message can't be larger than modulus.");
		}
		
		return m.modPow(e, n).toByteArray();
	}

	@Override
	public byte[] decrypt(byte[] cipher, RSAKey privateKey) {
		BigInteger d = privateKey.getExponent();
		BigInteger n = privateKey.getModulus();
		BigInteger c = new BigInteger(cipher);
		return c.modPow(d, n).toByteArray();
	}
}
