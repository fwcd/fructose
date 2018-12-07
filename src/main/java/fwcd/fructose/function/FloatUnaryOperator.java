package fwcd.fructose.function;

/**
 * A simple function that takes and returns a float.
 * May be used over {@link DoubleUnaryOperator} for
 * performance reasons.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface FloatUnaryOperator {
	float applyAsFloat(float value);
}
