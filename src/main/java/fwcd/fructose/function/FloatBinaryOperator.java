package fwcd.fructose.function;

import java.util.function.DoubleUnaryOperator;

/**
 * A simple function that takes and returns a float.
 * May be used over {@link DoubleUnaryOperator} for
 * performance reasons.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface FloatBinaryOperator {
	float applyAsFloat(float left, float right);
}
