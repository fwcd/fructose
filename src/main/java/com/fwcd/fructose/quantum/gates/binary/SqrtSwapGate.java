package com.fwcd.fructose.quantum.gates.binary;

import com.fwcd.fructose.math.Complex;
import com.fwcd.fructose.math.ComplexMatrix;
import com.fwcd.fructose.quantum.gates.MatrixGate;

/**
 * Half swaps the input qubits.
 * 
 * @author Fredrik
 *
 */
public class SqrtSwapGate extends MatrixGate {
	@Override
	protected ComplexMatrix getMatrix() {
		return new ComplexMatrix(new Complex[][] {
				{Complex.ONE, Complex.ZERO, Complex.ZERO, Complex.ZERO},
				{Complex.ZERO, new Complex(0.5F, 0.5F), new Complex(0.5F, -0.5F), Complex.ZERO},
				{Complex.ZERO, new Complex(0.5F, -0.5F), new Complex(0.5F, 0.5F), Complex.ZERO},
				{Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ONE}
		});
	}
}
