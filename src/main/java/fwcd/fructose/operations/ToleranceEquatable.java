package fwcd.fructose.operations;

/**
 * An object on which a tolerance equivalence relation
 * is defined.
 */
@FunctionalInterface
public interface ToleranceEquatable<T extends ToleranceEquatable<T>> {
	boolean equals(T rhs, double epsilon);
}
