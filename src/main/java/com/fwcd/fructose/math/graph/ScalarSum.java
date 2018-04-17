package com.fwcd.fructose.math.graph;

public class ScalarSum implements Scalar {
	private final Scalar[] summands;

	public ScalarSum(Scalar summandA, Scalar summandB) {
		summands = new Scalar[] {summandA, summandB};
	}
	
	public Scalar getSummandA() {
		return summands[0];
	}
	
	public Scalar getSummandB() {
		return summands[1];
	}
	
	@Override
	public String toString() {
		return "(" + getSummandA().toString() + " + " + getSummandB().toString() + ")";
	}
	
	@Override
	public Double compute() {
		return getSummandA().compute() + getSummandB().compute();
	}

	@Override
	public Scalar partialDerivative(Scalar arg) {
		return getSummandA().partialDerivative(arg).add(getSummandB().partialDerivative(arg));
	}

	@Override
	public boolean isConstant() {
		return getSummandA().isConstant() && getSummandB().isConstant();
	}
}
