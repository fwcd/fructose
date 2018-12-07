package fwcd.fructose.operations;

/** A generic division operation. */
@FunctionalInterface
public interface Divisible<R, O> {
	O divide(R rhs);
}
