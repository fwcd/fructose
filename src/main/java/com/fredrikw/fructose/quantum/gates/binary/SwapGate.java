package com.fredrikw.fructose.quantum.gates.binary;

import com.fredrikw.fructose.math.ComplexMatrix;
import com.fredrikw.fructose.quantum.gates.MatrixGate;

/**
 * Swaps the input qubits.
 * 
 * @author fredrik
 *
 */
public class SwapGate extends MatrixGate {
	@Override
	protected ComplexMatrix getMatrix() {
		return new ComplexMatrix(new float[][] {
				{1, 0, 0, 0},
				{0, 0, 1, 0},
				{0, 1, 0, 0},
				{0, 0, 0, 1}
		});
	}
}
