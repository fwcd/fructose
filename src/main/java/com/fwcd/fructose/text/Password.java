package com.fwcd.fructose.text;

/**
 * A mutable sequence of characters that is designed
 * for use with passwords.
 * 
 * The advantage of using this class instead of a
 * regular String is that it can be overwritten directly
 * in memory instead of relying on the GC to pick it up.
 * 
 * Additionally, it's toString() implementation does
 * not expose the characters (which could be logged accidentally)
 * and equals()/hashCode() does only compare object
 * identity, not the data itself.
 */
public final class Password implements AutoCloseable {
	private transient char[] chars;
	
	private Password(int length) {
		chars = new char[length];
		erase();
	}
	
	private Password(char... chars) { this.chars = chars; }
	
	public static Password newEmpty() { return new Password(0); }
	
	public static Password of(char... chars) { return new Password(chars); }
	
	public static Password ofLength(int length) { return new Password(length); }
	
	public char[] get() { return chars; }
	
	public void set(char... chars) { this.chars = chars; }
	
	public void setChar(int index, char value) { chars[index] = value; }
	
	public char getChar(int index) { return chars[index]; }
	
	public int getLength() { return chars.length; }
	
	public void fillWith(char c) {
		synchronized (chars) {
			for (int i=0; i<chars.length; i++) {
				chars[i] = c;
			}
		}
	}
	
	public void erase() { fillWith((char) 0); }
	
	@Override
	public void close() {
		erase();
	}
	
	@Override
	public String toString() {
		return "<Password>";
	}
	
	// Does not override equals() and hashCode() by design
}
