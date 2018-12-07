package fwcd.fructose.chiffre.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RSAKeyPair {
	private final BigInteger[] fermatPrimes = {
			BigInteger.valueOf(3),
			BigInteger.valueOf(5),
			BigInteger.valueOf(17),
			BigInteger.valueOf(257),
			BigInteger.valueOf(65537)
	};
	private final RSAKey publicKey;
	private final RSAKey privateKey;

	public RSAKeyPair(int bits) {
		Random random = new SecureRandom();
		BigInteger e = fermatPrimes[4];
		BigInteger p;
		BigInteger q;
		BigInteger n;
		BigInteger phi;
		
		do {
			p = BigInteger.probablePrime(bits, random);
			q = BigInteger.probablePrime(bits, random);
			n = p.multiply(q);
			phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		} while (p.equals(q) || !e.gcd(phi).equals(BigInteger.ONE));
		
		BigInteger d = e.modInverse(phi);
		
		publicKey = new RSAKey(e, n, bits);
		privateKey = new RSAKey(d, n, bits);
	}
	
	public RSAKey getPublicKey() { return publicKey; }
	
	public RSAKey getPrivateKey() { return privateKey; }
}
