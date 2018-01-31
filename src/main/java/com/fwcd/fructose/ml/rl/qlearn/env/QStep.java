package com.fwcd.fructose.ml.rl.qlearn.env;

import java.util.NoSuchElementException;

public class QStep<S extends QState<S, A>, A extends QAction> {
	private final int index;
	private final A action;
	private final double qValue;
	
	public QStep(A action) {
		this.action = action;
		index = -1;
		qValue = Double.NaN;
	}
	
	public QStep(A action, double qValue, int index) {
		this.action = action;
		this.qValue = qValue;
		this.index = index;
	}
	
	public A getAction() {
		return action;
	}
	
	public double getQValue() {
		if (Double.isNaN(qValue)) {
			throw new NoSuchElementException("No Q-value has been assigned to this step.");
		} else {
			return qValue;
		}
	}
	
	public int getIndex() {
		if (index < 0) {
			throw new NoSuchElementException("No index has been assigned to this step.");
		} else {
			return index;
		}
	}
}
