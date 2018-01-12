package com.fredrikw.fructose.quantum.gates.unary;

import com.fredrikw.fructose.math.ComplexMatrix;
import com.fredrikw.fructose.quantum.gates.MatrixGate;

/**
 * Inverts around the hypercomplex x+z-axis.
 * 
 * @author fredrik
 *
 */
public class HadamardGate extends MatrixGate {
	@Override
	protected ComplexMatrix getMatrix() {
		return new ComplexMatrix(new float[][] {
				{1, 1},
				{1, -1}
		}).multiply((float) (1 / Math.sqrt(2)));
	}

	@Override
	public String getSymbol() {
		return "H";
	}
}
