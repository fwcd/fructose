package com.fwcd.fructose.parsers.ebnf;

import com.fwcd.fructose.Option;
import com.fwcd.fructose.OptionInt;

/**
 * A token as in formal language theory. Every token
 * that is not a {@link Terminal} is a non-terminal token.
 * 
 * @author Fredrik
 *
 */
public interface Token {
	/**
	 * Fetches the amount of terminals in
	 * the sequence before the first non-matching
	 * terminal in reached. If this returns empty,
	 * it could not be matched at all.
	 */
	OptionInt matchCount(Terminal... sequence);
	
	boolean matches(Terminal... sequence);
	
	default Token concat(Token other) {
		return new ConcatCombo(this, other);
	}
	
	default Token or(Token other) {
		return new OrCombo(this, other);
	}
	
	default boolean matches(String sequence) {
		return matches(TokenUtils.asTerminals(sequence));
	}
	
	default Option<Terminal> asTerminal() {
		return Option.empty();
	}
}
