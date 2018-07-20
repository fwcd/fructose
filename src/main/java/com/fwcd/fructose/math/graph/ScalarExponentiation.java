package com.fwcd.fructose.math.graph;

public class ScalarExponentiation extends CompositeScalar {
	private final ScalarTerm[] parents;
	
	public ScalarExponentiation(ScalarTerm base, ScalarTerm exponent) {
		parents = new ScalarTerm[] {base, exponent};
	}
	
	@Override
	public Double compute() {
		return Math.pow(getBase().compute(), getExponent().compute());
	}
	
	@Override
	public String toString() {
		return "(" + getBase().toString() + " ^ " + getExponent().toString() + ")";
	}
	
	public ScalarTerm getBase() {
		return parents[0];
	}

	public ScalarTerm getExponent() {
		return parents[1];
	}

	@Override
	protected ScalarTerm[] getParents() {
		return parents;
	}

	@Override
	protected ScalarTerm partialDerivForParent(ScalarTerm parent) {
		ScalarTerm base = getBase();
		ScalarTerm exp = getExponent();
		
		if (parent.equals(base)) {
			return exp.multiply(base.pow(exp.sub(ConstScalar.ONE)));
		} else if (parent.equals(exp)) {
			return multiply(new ScalarLn(base));
		} else {
			return ConstScalar.ZERO;
		}
	}
}
