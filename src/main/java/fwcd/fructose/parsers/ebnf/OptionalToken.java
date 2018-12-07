package fwcd.fructose.parsers.ebnf;

import fwcd.fructose.OptionInt;

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
	public OptionInt matchCount(Terminal... sequence) {
		if (sequence.length > 0 && token.matches(sequence[0])) {
			return OptionInt.of(1);
		} else {
			return OptionInt.of(0);
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
