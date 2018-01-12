package com.fredrikw.fructose.quantum.core;

/**
 * A system of one or more determined qubits.<br><br>
 * 
 * Thus it contains a collapsed superposition.
 * If the original {@link QubitSuperpos} was initialized
 * with 3 qubits and it's superposition consisted of
 * 2^3 values, then this result will contain 3 values
 * again.
 * 
 * @author Fredrik
 *
 */
public interface QubitState {
	/**
	 * Creates a new {@link QubitState} containing all the bits of
	 * this state plus a given bit.
	 * 
	 * @param bit - The appended bit
	 * @return The concatenated state
	 */
	QubitState withBit(boolean bit);
	
	/**
	 * Fetches the resolved qubits.
	 * 
	 * @return The determined qubits of this layer.
	 */
	boolean[] getBits();
	
	/**
	 * @return An integer containing the binary representation of this qubit state
	 */
	int toInt();
	
	int qubitsAmount();
}
