package com.fredrikw.fructose.quantum.gates;

import com.fredrikw.fructose.math.ComplexMatrix;
import com.fredrikw.fructose.math.ComplexVector;
import com.fredrikw.fructose.math.ExtMath;

/**
 * A convenience class to represent matrix based
 * quantum gates.
 * 
 * @author Fredrik
 *
 */
public abstract class MatrixGate implements QuantumGate {
	private final ComplexMatrix identity = new ComplexMatrix(new float[][] {
			{1, 0},
			{0, 1}
	});
	private final ComplexMatrix matrix = getMatrix();
	private final int minQubits = minQubits();
	
	@Override
	public ComplexVector apply(ComplexVector possibleStates, int qubitIndex) {
		int totalQubits = ExtMath.log2Floor(possibleStates.size());
		
		if (qubitIndex > (totalQubits - minQubits)) {
			throw new IllegalArgumentException(
					"Qubit index "
					+ Integer.toString(qubitIndex)
					+ " too large for an input of "
					+ Integer.toString(totalQubits)
					+ " qubits and a gate requiring "
					+ Integer.toString(minQubits)
					+ " qubits!"
			);
		}
		
		ComplexMatrix customMatrix = getCustomMatrix(totalQubits, qubitIndex);
		try {
			return customMatrix.multiply(possibleStates);
		} catch (IllegalArgumentException e) {
			// TODO: Rework this case
			throw new IncompatibleGateException(getClass().getSimpleName(), possibleStates, matrix);
		}
	}

	private ComplexMatrix getCustomMatrix(int totalQubits, int qubitIndex) {
		ComplexMatrix result = null;
		
		int i = 0;
		while (i < totalQubits) {
			ComplexMatrix factor;
			
			if (i == qubitIndex) {
				factor = matrix;
				i += minQubits - 1;
			} else {
				factor = identity;
			}
			
			if (result == null) {
				result = factor;
			} else {
				result = result.kroneckerProduct(factor);
			}
			
			i++;
		}
		
		return result;
	}

	// TODO: See https://en.wikipedia.org/wiki/Quantum_gate - implement some gates
	
	/**
	 * Fetches the permutation matrix for this gate.
	 * 
	 * @return The permutation matrix
	 */
	protected abstract ComplexMatrix getMatrix();
	
	public int minQubits() {
		return ExtMath.log2Floor(matrix.width());
	}
}
