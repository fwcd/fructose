package com.fwcd.fructose.math.graph;

public class ScalarLn extends CompositeScalar {
	private final ScalarTerm[] parents;

	public ScalarLn(ScalarTerm arg) {
		parents = new ScalarTerm[] {arg};
	}
	
	public ScalarTerm getArgument() {
		return parents[0];
	}
	
	@Override
	public Double compute() {
		return Math.log(getArgument().compute());
	}

	@Override
	protected ScalarTerm[] getParents() {
		return parents;
	}

	@Override
	protected ScalarTerm partialDerivForParent(ScalarTerm parent) {
		return parent.reciprocal();
	}

	@Override
	public String toString() {
		return "ln(" + getArgument().toString() + ")";
	}
}
