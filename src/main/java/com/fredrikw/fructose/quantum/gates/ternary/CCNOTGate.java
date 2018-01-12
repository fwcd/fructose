package com.fredrikw.fructose.quantum.gates.ternary;

import com.fredrikw.fructose.math.ComplexMatrix;
import com.fredrikw.fructose.quantum.gates.MatrixGate;

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
