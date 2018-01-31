package com.fwcd.fructose.quantum.gates.ternary;

import com.fwcd.fructose.math.ComplexMatrix;
import com.fwcd.fructose.quantum.gates.MatrixGate;

/**
 * If the first two bits are true it flips the third bit.<br><br>
 * 
 * Also called ToffoliGate.
 * 
 * @author Fredrik
 *
 */
public class CCNOTGate extends MatrixGate {
	@Override
	protected ComplexMatrix getMatrix() {
		return new ComplexMatrix(new float[][] {
				{1, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 0, 0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0, 0, 0, 0},
				{0, 0, 0, 1, 0, 0, 0, 0},
				{0, 0, 0, 0, 1, 0, 0, 0},
				{0, 0, 0, 0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 1},
				{0, 0, 0, 0, 0, 0, 1, 0}
		});
	}
}
