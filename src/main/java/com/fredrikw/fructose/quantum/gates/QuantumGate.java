package com.fredrikw.fructose.quantum.gates;

import com.fredrikw.fructose.math.ComplexVector;

/**
 * A quantum gates takes a superposition vector as it's
 * input and produces a new superposition vector as it's
 * output.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface QuantumGate {
	/**
	 * Applies this quantum gate to a given vector.
	 * 
	 * @param possibleStates - The input superposition (or list of probabilities for possible qubit states)
	 * @param qubitIndex - The start qubit index of the qubits on which the gate will be applied
	 * @return An output superposition
	 */
	public ComplexVector apply(ComplexVector possibleStates, int qubitIndex);
	
	public default String getSymbol() {
		return "?";
	}
}
