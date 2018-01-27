package com.fredrikw.fructose.ml.rl.qlearn.env;

public class QStepResult<S extends QState<S, A>, A extends QAction> {
	private final double reward;
	private final S targetState;
	
	public QStepResult(double reward, S targetState) {
		this.reward = reward;
		this.targetState = targetState;
	}

	public double getReward() {
		return reward;
	}

	public S getNextState() {
		return targetState;
	}
}
