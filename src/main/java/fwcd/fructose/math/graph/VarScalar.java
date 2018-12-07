package fwcd.fructose.math.graph;

import java.util.NoSuchElementException;

public class VarScalar implements ScalarTerm {
	private String name;
	private Double value;
	private boolean hasValue;
	
	public VarScalar() {
		name = "{}";
		hasValue = false;
	}
	
	public VarScalar(String name) {
		this.name = name;
		hasValue = false;
	}
	
	public void set(double value) {
		this.value = value;
		hasValue = true;
	}
	
	@Override
	public Double compute() {
		if (hasValue) {
			return value;
		} else {
			throw new NoSuchElementException("Variable " + name + " has no value assigned!");
		}
	}
	
	@Override
	public String toString() {
		if (hasValue) {
			return value.toString();
		} else {
			return name;
		}
	}

	@Override
	public ScalarTerm partialDerivative(ScalarTerm arg) {
		if (hasValue && equals(arg)) {
			return ConstScalar.ONE;
		} else {
			return ConstScalar.ZERO;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public boolean isConstant() {
		return false;
	}
}
