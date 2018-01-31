package com.fwcd.fructose.quantum.gates.unary;

import com.fwcd.fructose.math.ComplexMatrix;
import com.fwcd.fructose.quantum.gates.MatrixGate;

/**
 * Inverts around the hypercomplex x-axis.<br><br>
 * 
 * Also referred to as NOT-Gate.
 * 
 * @author Fredrik
 *
 */
public class PauliXGate extends MatrixGate {
	@Override
	protected ComplexMatrix getMatrix() {
		return new ComplexMatrix(new float[][] {
				{0, 1},
				{1, 0}
		});
	}

	@Override
	public String getSymbol() {
		return "X";
	}
}
