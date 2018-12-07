package fwcd.fructose.math;

import fwcd.fructose.operations.AddSubMulDiv;
import fwcd.fructose.operations.ToleranceEquatable;

public interface Numeric<V extends Numeric<V>> extends
		AddSubMulDiv<V>,
		ToleranceEquatable<V> {
	/** Finds the multiplicative inverse. */
	V reciprocal();
	
	/** Find the additive inverse. */
	V negate();
	
	V add(Real rhs);
	
	V sub(Real rhs);
	
	V multiply(Real rhs);
	
	V divide(Real rhs);
}
