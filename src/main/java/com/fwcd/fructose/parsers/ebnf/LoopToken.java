package com.fwcd.fructose.parsers.ebnf;

import java.util.OptionalInt;

/**
 * A token that may be matched zero or
 * more times.
 * 
 * @author Fredrik
 *
 */
public class LoopToken implements Token {
	private final Token token;

	public LoopToken(Token token) {
		this.token = token;
	}

	@Override
	public OptionalInt matchCount(Terminal... sequence) {
		int matches = 0;
		
		for (Terminal terminal : sequence) {
			if (token.matches(terminal)) {
				matches++;
			} else {
				break;
			}
		}
		
		return OptionalInt.of(matches);
	}

	@Override
	public boolean matches(Terminal... sequence) {
		return matchCount(sequence).orElse(-1) == sequence.length;
	}
}
