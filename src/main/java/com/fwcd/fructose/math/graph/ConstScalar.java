package com.fwcd.fructose.math.graph;

public class ConstScalar implements ScalarTerm {
	public static final ConstScalar MINUS_ONE = new ConstScalar(-1);
	public static final ConstScalar ZERO = new ConstScalar(0);
	public static final ConstScalar ONE = new ConstScalar(1);
	
	private final Double value;
	
	public ConstScalar(double value) {
		this.value = value;
	}
	
	@Override
	public Double compute() {
		return value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public ScalarTerm partialDerivative(ScalarTerm arg) {
		return ZERO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ConstScalar other = (ConstScalar) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isConstant() {
		return true;
	}

	@Override
	public ScalarTerm add(ScalarTerm other) {
		if (other.isConstant()) {
			return new ConstScalar(value + other.compute());
		} else {
			return ScalarTerm.super.add(other);
		}
	}

	@Override
	public ScalarTerm sub(ScalarTerm other) {
		if (other.isConstant()) {
			return new ConstScalar(value - other.compute());
		} else {
			return ScalarTerm.super.sub(other);
		}
	}

	@Override
	public ScalarTerm multiply(ScalarTerm other) {
		if (other.isConstant()) {
			return new ConstScalar(value * other.compute());
		} else {
			return ScalarTerm.super.multiply(other);
		}
	}

	@Override
	public ScalarTerm divide(ScalarTerm other) {
		if (other.isConstant()) {
			return new ConstScalar(value / other.compute());
		} else {
			return ScalarTerm.super.divide(other);
		}
	}

	@Override
	public ScalarTerm pow(ScalarTerm other) {
		if (other.isConstant()) {
			return new ConstScalar(Math.pow(value, other.compute()));
		} else {
			return ScalarTerm.super.pow(other);
		}
	}

	@Override
	public ScalarTerm invert() {
		return new ConstScalar(-value);
	}

	@Override
	public ScalarTerm reciprocal() {
		return new ConstScalar(1D / value);
	}
}
