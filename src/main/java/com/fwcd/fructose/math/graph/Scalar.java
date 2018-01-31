package com.fwcd.fructose.math.graph;

public interface Scalar extends Term<Double> {
	/**
	 * Calculates the partial derivative with respect
	 * to a given argument.
	 * 
	 * @param arg - The variable we will differentiate (the other's are kept constant)
	 * @return del this / del arg (or null if there is none)
	 */
	Scalar partialDerivative(Scalar arg);
	
	boolean isConstant();
	
	default Scalar add(Scalar other) {
		if (other.isConstant()) {
			if (other.compute() == 0D) {
				return this;
			}
		} else if (isConstant()) {
			if (compute() == 0D) {
				return other;
			}
		}
		
		return new ScalarSum(this, other);
	}
	
	default Scalar sub(Scalar other) {
		return add(other.invert());
	}
	
	default Scalar multiply(Scalar other) {
		if (other.isConstant()) {
			double val = other.compute();
			if (val == 0D) {
				return ConstScalar.ZERO;
			} else if (val == 1D) {
				return this;
			}
		} else if (isConstant()) {
			double val = compute();
			if (val == 0D) {
				return ConstScalar.ZERO;
			} else if (val == 1D) {
				return other;
			}
		}
		
		return new ScalarProduct(this, other);
	}
	
	default Scalar divide(Scalar other) {
		return multiply(other.reciprocal());
	}
	
	default Scalar pow(Scalar other) {
		if (other.isConstant()) {
			if (other.compute() == 1D) {
				return this;
			}
		} else if (isConstant()) {
			if (compute() == 1D) {
				return this;
			}
		}
		
		return new ScalarExponentiation(this, other);
	}
	
	default Scalar invert() {
		return new ScalarInversion(this);
	}
	
	default Scalar reciprocal() {
		return new ScalarExponentiation(this, ConstScalar.MINUS_ONE);
	}
}
