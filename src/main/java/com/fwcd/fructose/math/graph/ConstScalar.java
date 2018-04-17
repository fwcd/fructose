package com.fwcd.fructose.math.graph;

public class ConstScalar implements Scalar {
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
	public Scalar partialDerivative(Scalar arg) {
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
	public Scalar add(Scalar other) {
		if (other.isConstant()) {
			return new ConstScalar(value + other.compute());
		} else {
			return Scalar.super.add(other);
		}
	}

	@Override
	public Scalar sub(Scalar other) {
		if (other.isConstant()) {
			return new ConstScalar(value - other.compute());
		} else {
			return Scalar.super.sub(other);
		}
	}

	@Override
	public Scalar multiply(Scalar other) {
		if (other.isConstant()) {
			return new ConstScalar(value * other.compute());
		} else {
			return Scalar.super.multiply(other);
		}
	}

	@Override
	public Scalar divide(Scalar other) {
		if (other.isConstant()) {
			return new ConstScalar(value / other.compute());
		} else {
			return Scalar.super.divide(other);
		}
	}

	@Override
	public Scalar pow(Scalar other) {
		if (other.isConstant()) {
			return new ConstScalar(Math.pow(value, other.compute()));
		} else {
			return Scalar.super.pow(other);
		}
	}

	@Override
	public Scalar invert() {
		return new ConstScalar(-value);
	}

	@Override
	public Scalar reciprocal() {
		return new ConstScalar(1D / value);
	}
}
