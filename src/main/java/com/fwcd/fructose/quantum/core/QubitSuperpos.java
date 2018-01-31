package com.fwcd.fructose.quantum.core;

import com.fwcd.fructose.quantum.gates.QuantumGate;

/**
 * A system of one or more non-determined qubits.<br><br>
 * 
 * Thus it encapsulates a superposition of every possible combination of the given qubits
 * (as binary index in the vector) mapped to it's probability
 * (as the value of the given index in the vector).<br><br>
 * 
 * The size of this superposition vector grows exponentially
 * (2^n) to the amount of qubits.
 * 
 * @author Fredrik
 *
 */
public interface QubitSuperpos {
	/**
	 * Applies the given quantum gate to this superposition.
	 * 
	 * @param gate - The gate
	 * @param qubitIndex - The first qubit index of the qubits to which the gate will be applied
	 */
	QubitSuperpos apply(QuantumGate gate, int qubitIndex);
	
	/**
	 * Fetches the amount of qubits.
	 * 
	 * @return The amount of qubits
	 */
	int qubitsAmount();
	
	/**
	 * Collapses this superposition to a series of
	 * zeros and ones.
	 * 
	 * @return The collapsed state of qubits
	 */
	QubitState collapse();
	
	/**
	 * Fetches the probability of a given state of
	 * qubits, provided this superposition has this state.
	 * 
	 * @param state - The searched state
	 * @return The probability of it being the state used when collapsing this superposition
	 */
	float probabilityOf(QubitState state);
	
	default QubitSuperpos apply(QuantumGate gate) {
		return apply(gate, 0);
	}
}
