package com.fredrikw.fructose.quantum.gates.unary;

import com.fredrikw.fructose.math.ComplexMatrix;
import com.fredrikw.fructose.quantum.gates.MatrixGate;

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
