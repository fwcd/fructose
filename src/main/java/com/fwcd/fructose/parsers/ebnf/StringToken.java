package com.fwcd.fructose.parsers.ebnf;

import java.util.OptionalInt;

/**
 * Represents a string token that consists
 * of concatenated (character) terminals.
 * 
 * @author Fredrik
 *
 */
public class StringToken implements Token {
	private final Token token;
	
	public StringToken(String str) {
		token = new ConcatCombo(TokenUtils.asTerminals(str));
	}
	
	@Override
	public OptionalInt matchCount(Terminal... sequence) {
		return token.matchCount(sequence);
	}

	@Override
	public boolean matches(Terminal... sequence) {
		return token.matches(sequence);
	}
}
