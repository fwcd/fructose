package fwcd.fructose.operations;

@FunctionalInterface
public interface ToleranceEquatable<T extends ToleranceEquatable<T>> {
	boolean equals(T rhs, double tolerance);
}
