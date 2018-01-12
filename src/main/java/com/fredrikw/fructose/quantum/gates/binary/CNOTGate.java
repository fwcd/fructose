package com.fredrikw.fructose.quantum.gates.binary;

import com.fredrikw.fructose.math.ComplexMatrix;
import com.fredrikw.fructose.quantum.gates.MatrixGate;

/**
 * Inverts the second input qubit if the first
 * input qubit is enabled.<br><br>
 * 
 * Thus it XOR's the bits, stores the result in the
 * second bit and leaves the first bit unchanged.
 * 
 * @author Fredrik
 *
 */
public class CNOTGate extends MatrixGate {
	@Override
	protected ComplexMatrix getMatrix() {
		return new ComplexMatrix(new float[][] {
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 0, 1},
				{0, 0, 1, 0}
		});
	}
}
