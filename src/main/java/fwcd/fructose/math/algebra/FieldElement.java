package fwcd.fructose.math.algebra;

import fwcd.fructose.operations.Divisible;

/**
 * An element from a mathematical set on which addition, subtraction,
 * multiplication and division is defined.
 * 
 * <p>
 * Multiplication is guaranteed to be commutative!
 * </p>
 */
public interface FieldElement<V extends FieldElement<V>> extends
		DivisionRingElement<V>,
		Divisible<V, V> {
	@Override
	default V leftDivide(V rhs) { return divide(rhs); }
	
	@Override
	default V rightDivide(V rhs) { return divide(rhs); }
}
