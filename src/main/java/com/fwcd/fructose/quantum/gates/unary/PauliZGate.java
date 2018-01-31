package com.fwcd.fructose.quantum.gates.unary;

import com.fwcd.fructose.math.ComplexMatrix;
import com.fwcd.fructose.quantum.gates.MatrixGate;

/**
 * Inverts around the hypercomplex z-axis.
 * 
 * @author fredrik
 *
 */
public class PauliZGate extends MatrixGate {
	@Override
	protected ComplexMatrix getMatrix() {
		return new ComplexMatrix(new float[][] {
				{1, 0},
				{0, -1}
		});
	}

	@Override
	public String getSymbol() {
		return "Z";
	}
}
