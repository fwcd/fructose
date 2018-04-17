package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;

import org.junit.Test;

import com.fwcd.fructose.chiffre.rsa.RSA;
import com.fwcd.fructose.chiffre.rsa.RSAKeyPair;

public class RSATest {
	@Test
	public void test() {
		RSAKeyPair keyPair = new RSAKeyPair(32);
		RSA rsa = new RSA();
		Charset charset = Charset.defaultCharset();
		
		String message = "hello";
		byte[] cipher = rsa.encrypt(message.getBytes(charset), keyPair.getPublicKey());
		assertEquals(message, new String(rsa.decrypt(cipher, keyPair.getPrivateKey()), charset));
	}
}
