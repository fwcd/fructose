package fwcd.fructose.math;

import fwcd.fructose.math.algebra.FieldElement;
import fwcd.fructose.operations.ToleranceEquatable;

/**
 * A field element on which scalar addition,
 * scalar subtraction, scalar multiplication,
 * scalar division and a tolerance equivalence
 * relation are defined.
 * 
 * <p>These rules are sastified by the real numbers
 * and common supersets (such as complex numbers).</p>
 */
public interface Numeric<V extends Numeric<V>> extends
		FieldElement<V>,
		ToleranceEquatable<V> {
	V add(Real rhs);
	
	V sub(Real rhs);
	
	V multiply(Real rhs);
	
	V divide(Real rhs);
}
