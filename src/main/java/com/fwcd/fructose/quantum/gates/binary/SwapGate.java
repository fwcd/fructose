package com.fwcd.fructose.quantum.gates.binary;

import com.fwcd.fructose.math.ComplexMatrix;
import com.fwcd.fructose.quantum.gates.MatrixGate;

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
