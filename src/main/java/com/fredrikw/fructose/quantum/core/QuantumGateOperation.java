package com.fredrikw.fructose.quantum.core;

import com.fredrikw.fructose.quantum.gates.QuantumGate;

public class QuantumGateOperation implements QubitOperation {
	private final QuantumGate gate;
	private final int qubitIndex;
	
	public QuantumGateOperation(QuantumGate gate, int qubitIndex) {
		this.gate = gate;
		this.qubitIndex = qubitIndex;
	}
	
	public QuantumGate getGate() {
		return gate;
	}
	
	public int getQubitIndex() {
		return qubitIndex;
	}
	
	@Override
	public QubitSuperpos apply(QubitSuperpos superpos) {
		return superpos.apply(gate, qubitIndex);
	}
	
	@Override
	public String toString() {
		return gate.getClass().getSimpleName() + " #" + Integer.toString(qubitIndex);
	}
}
