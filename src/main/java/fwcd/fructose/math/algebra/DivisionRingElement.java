package fwcd.fructose.math.algebra;

import fwcd.fructose.operations.Addable;
import fwcd.fructose.operations.Multipliable;
import fwcd.fructose.operations.Subtractable;

/**
 * An element from a mathematical set on
 * which addition, subtraction and multiplication is defined.
 * Every nonzero element will have a multiplicative
 * inverse too.
 * 
 * <p>Multiplication is NOT required to be commutative!</p>
 */
public interface DivisionRingElement<V extends DivisionRingElement<V>> extends
		Addable<V, V>,
		Subtractable<V, V>,
		Multipliable<V, V> {
	/** Finds the multiplicative inverse. */
	V reciprocal();
	
	/** Find the additive inverse. */
	V negate();
	
	/** Computes (rhs^-1 * this) */
	V leftDivide(V rhs);
	
	/** Computes (this * rhs^-1) */
	V rightDivide(V rhs);
}
