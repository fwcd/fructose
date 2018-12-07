package fwcd.fructose.operations;

/** A generic multiplication operation. */
@FunctionalInterface
public interface Multipliable<R, O> {
	O multiply(R rhs);
}
