package com.fredrikw.fructose.parsers.ebnf;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.stream.Stream;

/**
 * An array of "concatenated" tokens that need to be
 * matched sequentially.
 * 
 * @author Fredrik
 *
 */
public class ConcatCombo implements Token {
	private final Token[] tokens;

	public ConcatCombo(Token... tokens) {
		this.tokens = tokens;
	}

	@Override
	public OptionalInt matchCount(Terminal... sequence) {
		Terminal[] seq = sequence;
		int matchCount = 0;
		
		for (Token token : tokens) {
			OptionalInt count = token.matchCount(seq);
			
			if (count.isPresent()) {
				int unwrappedCount = count.orElseThrow(NoSuchElementException::new);
				
				if (unwrappedCount <= seq.length) {
					seq = Arrays.copyOfRange(seq, unwrappedCount, seq.length);
				}
				
				matchCount += unwrappedCount;
			} else {
				return OptionalInt.empty();
			}
		}
		
		return OptionalInt.of(matchCount);
	}

	@Override
	public Token concat(Token other) {
		Token[] otherTokens;
		
		if (other instanceof ConcatCombo) {
			otherTokens = ((ConcatCombo) other).tokens;
		} else {
			otherTokens = new Token[] {other};
		}
		
		return new ConcatCombo(Stream.concat(Arrays.stream(tokens), Arrays.stream(otherTokens)).toArray(Token[]::new));
	}
	
	@Override
	public String toString() {
		return Arrays.toString(tokens);
	}

	@Override
	public boolean matches(Terminal... sequence) {
		return sequence.length == matchCount(sequence).orElse(-1);
	}
}
