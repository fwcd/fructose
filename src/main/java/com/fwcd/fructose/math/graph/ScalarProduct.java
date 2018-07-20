package com.fwcd.fructose.math.graph;

/**
 * A multiplication of two scalars. <b>This class
 * does NOT refer to the vector dot product
 * which is sometimes also called "scalar product".</b>
 * 
 * @author Fredrik
 *
 */
public class ScalarProduct implements ScalarTerm {
	private final ScalarTerm[] factors;

	public ScalarProduct(ScalarTerm factorA, ScalarTerm factorB) {
		factors = new ScalarTerm[] {factorA, factorB};
	}
	
	@Override
	public String toString() {
		return "(" + getFactorA().toString() + " * " + getFactorB().toString() + ")";
	}
	
	public ScalarTerm getFactorA() {
		return factors[0];
	}
	
	public ScalarTerm getFactorB() {
		return factors[1];
	}
	
	@Override
	public Double compute() {
		return getFactorA().compute() * getFactorB().compute();
	}

	@Override
	public ScalarTerm partialDerivative(ScalarTerm arg) {
		ScalarTerm u = getFactorA();
		ScalarTerm v = getFactorB();
		ScalarTerm uDeriv = u.partialDerivative(arg);
		ScalarTerm vDeriv = v.partialDerivative(arg);
		
		return u.multiply(vDeriv).add(uDeriv.multiply(v));
	}

	@Override
	public boolean isConstant() {
		return getFactorA().isConstant() && getFactorB().isConstant();
	}
}
