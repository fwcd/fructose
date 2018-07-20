package com.fwcd.fructose.math;

import com.fwcd.fructose.operations.AddSubMulDiv;
import com.fwcd.fructose.operations.ToleranceEquatable;

public interface Numeric<V extends Numeric<V>> extends
		AddSubMulDiv<V>,
		ToleranceEquatable<V> {
	V reciprocal();
	
	V add(Real rhs);
	
	V sub(Real rhs);
	
	V multiply(Real rhs);
	
	V divide(Real rhs);
}
