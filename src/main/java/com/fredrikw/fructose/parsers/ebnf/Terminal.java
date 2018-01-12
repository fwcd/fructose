package com.fredrikw.fructose.parsers.ebnf;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * A constant token that does not consist
 * of further tokens.
 * 
 * @author Fredrik
 *
 */
public class Terminal implements Token {
	private final char syntax;

	public Terminal(char syntax) {
		this.syntax = syntax;
	}
	
	public char getSyntax() {
		return syntax;
	}

	@Override
	public boolean matches(Terminal... sequence) {
		return sequence.length == 1 && equals(sequence[0]);
	}

	@Override
	public Optional<Terminal> asTerminal() {
		return Optional.of(this);
	}

	@Override
	public OptionalInt matchCount(Terminal... sequence) {
		return sequence.length > 0 && equals(sequence[0]) ? OptionalInt.of(1) : OptionalInt.empty();
	}
	
	@Override
	public String toString() {
		return "'" + Character.toString(syntax) + "'";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + syntax;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Terminal other = (Terminal) obj;
		if (syntax != other.syntax) {
			return false;
		}
		return true;
	}
}
