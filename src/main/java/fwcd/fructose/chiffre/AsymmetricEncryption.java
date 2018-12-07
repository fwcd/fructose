package fwcd.fructose.chiffre;

public interface AsymmetricEncryption<P, S> {
	byte[] encrypt(byte[] message, P publicKey);
	
	byte[] decrypt(byte[] cipher, S privateKey);
}
