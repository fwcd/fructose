package com.fredrikw.fructose.quantum.gates.ternary;

import com.fredrikw.fructose.math.ComplexMatrix;
import com.fredrikw.fructose.quantum.gates.MatrixGate;

/**
 * Performs a controlled swap of the last two bits.<br><br>
 * 
 * Also called FredkinGate.
 * 
 * @author Fredrik
 *
 */
public class CSwapGate extends MatrixGate {
	@Override
	protected ComplexMatrix getMatrix() {
		return new ComplexMatrix(new float[][] {
				{1, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 0, 0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0, 0, 0, 0},
				{0, 0, 0, 1, 0, 0, 0, 0},
				{0, 0, 0, 0, 1, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 0},
				{0, 0, 0, 0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 1}
		});
	}
}
