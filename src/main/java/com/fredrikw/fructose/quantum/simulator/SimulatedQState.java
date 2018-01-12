package com.fredrikw.fructose.quantum.simulator;

import java.util.Arrays;

import com.fredrikw.fructose.math.ExtMath;
import com.fredrikw.fructose.quantum.core.QubitState;

public class SimulatedQState implements QubitState {
	private final boolean[] resolvedQubits; // Size = amount of qubits
	
	public SimulatedQState(int binaryRepresentation, int qubitsAmount) {
		char[] bitChars = ExtMath.binaryString(binaryRepresentation, qubitsAmount).toCharArray();
		resolvedQubits = new boolean[bitChars.length];
		
		int i = 0;
		for (char bitChar : bitChars) {
			resolvedQubits[i] = (bitChar == '1');
			i++;
		}
	}
	
	public SimulatedQState(boolean... resolvedQubits) {
		this.resolvedQubits = resolvedQubits;
	}
	
	@Override
	public QubitState withBit(boolean bit) {
		int bitsAmount = resolvedQubits.length;
		boolean[] newQubits = new boolean[bitsAmount + 1];
		
		System.arraycopy(resolvedQubits, 0, newQubits, 0, bitsAmount);
		newQubits[bitsAmount] = bit;
		
		return new SimulatedQState(newQubits);
	}
	
	@Override
	public boolean[] getBits() {
		return resolvedQubits;
	}
	
	@Override
	public int toInt() {
		int v = 0;
		
		for (boolean bit : resolvedQubits) {
			v = (v << 1) | (bit ? 1 : 0);
		}
		
		return v;
	}
	
	@Override
	public String toString() {
		String result = "[State] ";
		
		for (boolean bit : resolvedQubits) {
			result += bit ? "1" : "0";
		}
		
		return result;
	}

	@Override
	public int qubitsAmount() {
		return resolvedQubits.length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(resolvedQubits);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SimulatedQState other = (SimulatedQState) obj;
		if (!Arrays.equals(resolvedQubits, other.resolvedQubits)) {
			return false;
		}
		return true;
	}
}
