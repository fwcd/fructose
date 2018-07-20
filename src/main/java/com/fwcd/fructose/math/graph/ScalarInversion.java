package com.fwcd.fructose.math.graph;

public class ScalarInversion extends CompositeScalar {
	private final ScalarTerm[] parents;

	public ScalarInversion(ScalarTerm arg) {
		parents = new ScalarTerm[] {arg};
	}
	
	@Override
	public Double compute() {
		return -getArgument().compute();
	}
	
	public ScalarTerm getArgument() {
		return parents[0];
	}

	@Override
	protected ScalarTerm[] getParents() {
		return parents;
	}

	@Override
	protected ScalarTerm partialDerivForParent(ScalarTerm parent) {
		return ConstScalar.MINUS_ONE;
	}

	@Override
	public String toString() {
		return "-" + getArgument().toString();
	}
}
