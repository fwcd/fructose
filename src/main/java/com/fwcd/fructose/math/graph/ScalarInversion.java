package com.fwcd.fructose.math.graph;

public class ScalarInversion extends CompositeScalar {
	private final Scalar[] parents;

	public ScalarInversion(Scalar arg) {
		parents = new Scalar[] {arg};
	}
	
	@Override
	public Double compute() {
		return -getArgument().compute();
	}
	
	public Scalar getArgument() {
		return parents[0];
	}

	@Override
	protected Scalar[] getParents() {
		return parents;
	}

	@Override
	protected Scalar partialDerivForParent(Scalar parent) {
		return ConstScalar.MINUS_ONE;
	}

	@Override
	public String toString() {
		return "-" + getArgument().toString();
	}
}
