package fwcd.fructose.operations;

/** A generic addition operation. */
@FunctionalInterface
public interface Addable<R, O> {
	O add(R rhs);
}
