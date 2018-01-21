package com.fredrikw.fructose.math.graph;

public class ScalarLn extends CompositeScalar {
	private final Scalar[] parents;

	public ScalarLn(Scalar arg) {
		parents = new Scalar[] {arg};
	}
	
	public Scalar getArgument() {
		return parents[0];
	}
	
	@Override
	public Double compute() {
		return Math.log(getArgument().compute());
	}

	@Override
	protected Scalar[] getParents() {
		return parents;
	}

	@Override
	protected Scalar partialDerivForParent(Scalar parent) {
		return parent.reciprocal();
	}

	@Override
	public String toString() {
		return "ln(" + getArgument().toString() + ")";
	}
}
