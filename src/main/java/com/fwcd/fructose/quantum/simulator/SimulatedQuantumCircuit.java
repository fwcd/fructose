package com.fwcd.fructose.quantum.simulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fwcd.fructose.quantum.core.QuantumGateOperation;
import com.fwcd.fructose.quantum.core.QubitState;
import com.fwcd.fructose.quantum.core.QubitSuperpos;
import com.fwcd.fructose.quantum.gates.QuantumGate;

public class SimulatedQuantumCircuit implements Iterable<QuantumGateOperation> {
	private QubitState inputState = new SimulatedQState();
	private List<QuantumGateOperation> operations = new ArrayList<>();
	
	public void setInputQubit(int index, boolean value) {
		inputState.getBits()[index] = value;
	}
	
	public QubitState getInputState() {
		return inputState;
	}
	
	public int qubitsAmount() {
		return inputState.qubitsAmount();
	}
	
	public void addQubit(boolean initialState) {
		inputState = inputState.withBit(initialState);
	}
	
	public void addOperation(QuantumGate gate, int qubitIndex) {
		operations.add(new QuantumGateOperation(gate, qubitIndex));
	}
	
	public QubitSuperpos compute() {
		QubitSuperpos superpos = new SimulatedSuperpos(inputState);
		
		for (QuantumGateOperation operation : operations) {
			superpos = operation.apply(superpos);
		}
		
		return superpos;
	}
	
	public QubitState computeResult() {
		return compute().collapse();
	}

	@Override
	public Iterator<QuantumGateOperation> iterator() {
		return operations.iterator();
	}
	
	@Override
	public String toString() {
		return operations.toString();
	}
}
