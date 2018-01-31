package com.fwcd.fructose.math.graph;

/**
 * A multiplication of two scalars. <b>This class
 * does NOT refer to the vector dot product
 * which is sometimes also called "scalar product".</b>
 * 
 * @author Fredrik
 *
 */
public class ScalarProduct implements Scalar {
	private final Scalar[] factors;

	public ScalarProduct(Scalar factorA, Scalar factorB) {
		factors = new Scalar[] {factorA, factorB};
	}
	
	@Override
	public String toString() {
		return "(" + getFactorA().toString() + " * " + getFactorB().toString() + ")";
	}
	
	public Scalar getFactorA() {
		return factors[0];
	}
	
	public Scalar getFactorB() {
		return factors[1];
	}
	
	@Override
	public Double compute() {
		return getFactorA().compute() * getFactorB().compute();
	}

	@Override
	public Scalar partialDerivative(Scalar arg) {
		Scalar u = getFactorA();
		Scalar v = getFactorB();
		Scalar uDeriv = u.partialDerivative(arg);
		Scalar vDeriv = v.partialDerivative(arg);
		
		return u.multiply(vDeriv).add(uDeriv.multiply(v));
	}

	@Override
	public boolean isConstant() {
		return getFactorA().isConstant() && getFactorB().isConstant();
	}
}
