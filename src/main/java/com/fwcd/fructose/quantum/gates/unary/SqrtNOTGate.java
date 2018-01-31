package com.fwcd.fructose.quantum.gates.unary;

import com.fwcd.fructose.math.Complex;
import com.fwcd.fructose.math.ComplexMatrix;
import com.fwcd.fructose.quantum.gates.MatrixGate;

public class SqrtNOTGate extends MatrixGate {
	@Override
	protected ComplexMatrix getMatrix() {
		return new ComplexMatrix(new Complex[][] {
				{new Complex(1, 1), new Complex(1, -1)},
				{new Complex(1, -1), new Complex(1, 1)}
		}).multiply(0.5F);
	}

	@Override
	public String getSymbol() {
		return "sqrt(NOT)";
	}
}
