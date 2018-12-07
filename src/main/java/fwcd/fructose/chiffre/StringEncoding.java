package fwcd.fructose.chiffre;

public interface StringEncoding {
	byte[] encode(String data);
	
	String decode(byte[] data);
}
