package com.fredrikw.fructose.quantum.simulator;

import com.fredrikw.fructose.Distribution;
import com.fredrikw.fructose.math.ComplexVector;
import com.fredrikw.fructose.math.ExtMath;
import com.fredrikw.fructose.quantum.core.QubitState;
import com.fredrikw.fructose.quantum.core.QubitSuperpos;
import com.fredrikw.fructose.quantum.gates.QuantumGate;

public class SimulatedSuperpos implements QubitSuperpos {
	private final int qubitsAmount;
	private final ComplexVector possibleStates; // Contains the probabilities for the possible states; Size = 2 ^ amount of qubits
	
	public SimulatedSuperpos(boolean... initialQubitValues) {
		this(new SimulatedQState(initialQubitValues));
	}
	
	/**
	 * Constructs a new qubit superposition from the given
	 * input state. Note that the superposition requires
	 * exponentially (2^n) more space for each qubit.
	 * 
	 * @param state - The initial qubits state
	 */
	public SimulatedSuperpos(QubitState state) {
		qubitsAmount = state.qubitsAmount();
		float[] probabilities = new float[(int) Math.pow(2, qubitsAmount)];
		int givenState = state.toInt();
		
		for (int i=0; i<probabilities.length; i++) {
			probabilities[i] = (i == givenState) ? 1 : 0;
		}
		
		possibleStates = new ComplexVector(probabilities);
	}
	
	private SimulatedSuperpos(ComplexVector possibleStates) {
		this.possibleStates = possibleStates;
		qubitsAmount = ExtMath.log2Floor(possibleStates.size());
	}

	@Override
	public QubitSuperpos apply(QuantumGate gate) {
		return apply(gate, 0);
	}
	
	@Override
	public QubitSuperpos apply(QuantumGate gate, int qubitIndex) {
		return new SimulatedSuperpos(gate.apply(possibleStates, qubitIndex));
	}
	
	@Override
	public int qubitsAmount() {
		return qubitsAmount;
	}
	
	private Distribution<Integer> asDistribution() {
		Distribution<Integer> dist = new Distribution<>();
		
		for (int i=0; i<possibleStates.size(); i++) {
			dist.add(i, (float) possibleStates.get(i).absSquared());
		}
		
		return dist;
	}
	
	@Override
	public QubitState collapse() {
		return new SimulatedQState(asDistribution().pickStochastically(), qubitsAmount);
	}
	
	@Override
	public float probabilityOf(QubitState state) {
		try {
			return (float) possibleStates.get(state.toInt()).absSquared();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Not a valid qubit state!");
		}
	}
	
	@Override
	public String toString() {
		String result = "[Superposition]:\n";
		int qubits = qubitsAmount();
		
		for (int state=0; state<possibleStates.size(); state++) {
			float probability = (float) possibleStates.get(state).absSquared();
			
			result += "    " + ExtMath.binaryString(state, qubits) + " -> " + Float.toString(probability) + "\n";
		}
		
		return result;
	}
}
