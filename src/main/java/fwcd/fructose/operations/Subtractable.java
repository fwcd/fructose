package fwcd.fructose.operations;

/** A generic subtraction operation. */
@FunctionalInterface
public interface Subtractable<R, O> {
	O sub(R rhs);
}
