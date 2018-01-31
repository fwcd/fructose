package com.fwcd.fructose.ml.function;

import java.io.Serializable;

/**
 * A differentiable float function.
 * 
 * @author Fredrik
 *
 */
public interface DiffFunction extends Serializable {
	/**
	 * @return The result of the function at x
	 */
	float f(float x);

	/**
	 * @return The first derivative/slope of the function at x
	 */
	float df(float x);
	
	/**
	 * @return df(x) using pre-computed f(x) or Float.NaN by default
	 */
	default float dfUsingF(float f) {
		return Float.NaN;
	}
	
	/**
	 * @return Whether dfUsingF(...) will yield a valid result
	 */
	default boolean canComputeDfUsingF() {
		return false;
	}
}
