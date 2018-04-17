package com.fwcd.fructose.parsers.ebnf;

public class TokenUtils {
	public static Terminal[] asTerminals(String s) {
		Terminal[] result = new Terminal[s.length()];
		int i = 0;
		for (char c : s.toCharArray()) {
			result[i] = new Terminal(c);
			i++;
		}
		return result;
	}
}
