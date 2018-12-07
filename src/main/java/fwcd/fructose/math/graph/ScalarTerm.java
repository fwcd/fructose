package fwcd.fructose.math.graph;

public interface ScalarTerm extends Term<Double> {
	/**
	 * Calculates the partial derivative with respect
	 * to a given argument.
	 * 
	 * @param arg - The variable we will differentiate (the other's are kept constant)
	 * @return del this / del arg (or null if there is none)
	 */
	ScalarTerm partialDerivative(ScalarTerm arg);
	
	boolean isConstant();
	
	default ScalarTerm add(ScalarTerm other) {
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
	
	default ScalarTerm sub(ScalarTerm other) {
		return add(other.invert());
	}
	
	default ScalarTerm multiply(ScalarTerm other) {
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
	
	default ScalarTerm divide(ScalarTerm other) {
		return multiply(other.reciprocal());
	}
	
	default ScalarTerm pow(ScalarTerm other) {
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
	
	default ScalarTerm invert() {
		return new ScalarInversion(this);
	}
	
	default ScalarTerm reciprocal() {
		return new ScalarExponentiation(this, ConstScalar.MINUS_ONE);
	}
}
