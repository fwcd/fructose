package com.fredrikw.fructose.math.graph;

public class ScalarExponentiation extends CompositeScalar {
	private final Scalar[] parents;
	
	public ScalarExponentiation(Scalar base, Scalar exponent) {
		parents = new Scalar[] {base, exponent};
	}
	
	@Override
	public Double compute() {
		return Math.pow(getBase().compute(), getExponent().compute());
	}
	
	@Override
	public String toString() {
		return "(" + getBase().toString() + " ^ " + getExponent().toString() + ")";
	}
	
	public Scalar getBase() {
		return parents[0];
	}

	public Scalar getExponent() {
		return parents[1];
	}

	@Override
	protected Scalar[] getParents() {
		return parents;
	}

	@Override
	protected Scalar partialDerivForParent(Scalar parent) {
		Scalar base = getBase();
		Scalar exp = getExponent();
		
		if (parent.equals(base)) {
			return exp.multiply(base.pow(exp.sub(ConstScalar.ONE)));
		} else if (parent.equals(exp)) {
			return multiply(new ScalarLn(base));
		} else {
			return ConstScalar.ZERO;
		}
	}
}
