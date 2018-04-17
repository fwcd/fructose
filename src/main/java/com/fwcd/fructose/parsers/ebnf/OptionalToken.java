package com.fwcd.fructose.parsers.ebnf;

import java.util.OptionalInt;

/**
 * A token that may be matched exactly zero or
 * one times.
 * 
 * @author Fredrik
 *
 */
public class OptionalToken implements Token {
	private final Token token;

	public OptionalToken(Token token) {
		this.token = token;
	}

	@Override
	public OptionalInt matchCount(Terminal... sequence) {
		if (sequence.length > 0 && token.matches(sequence[0])) {
			return OptionalInt.of(1);
		} else {
			return OptionalInt.of(0);
		}
	}

	@Override
	public boolean matches(Terminal... sequence) {
		if (sequence.length == 1) {
			return token.matches(sequence[0]);
		} else {
			return sequence.length == 0;
		}
	}
}
