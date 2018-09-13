package com.fwcd.fructose.parsers.ebnf;

import com.fwcd.fructose.Option;
import com.fwcd.fructose.OptionInt;

/**
 * A constant token that does not consist
 * of further tokens.
 * 
 * @author Fredrik
 *
 */
public class Terminal implements Token {
	private final String syntax;

	public Terminal(String syntax) {
		this.syntax = syntax;
	}
	
	public Terminal(char syntax) {
		this.syntax = Character.toString(syntax);
	}
	
	public String getSyntax() {
		return syntax;
	}

	@Override
	public boolean matches(Terminal... sequence) {
		return sequence.length == 1 && equals(sequence[0]);
	}

	@Override
	public Option<Terminal> asTerminal() {
		return Option.of(this);
	}

	@Override
	public OptionInt matchCount(Terminal... sequence) {
		return sequence.length > 0 && equals(sequence[0]) ? OptionInt.of(1) : OptionInt.empty();
	}
	
	@Override
	public String toString() {
		return "'" + syntax + "'";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((syntax == null) ? 0 : syntax.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Terminal other = (Terminal) obj;
		if (syntax == null) {
			if (other.syntax != null)
				return false;
		} else if (!syntax.equals(other.syntax))
			return false;
		return true;
	}
}
