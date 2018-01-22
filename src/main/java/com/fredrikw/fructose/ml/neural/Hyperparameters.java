package com.fredrikw.fructose.ml.neural;

public class Hyperparameters {
	private final double learningRate;
	
	public Hyperparameters(
			double learningRate
	) {
		this.learningRate = learningRate;
	}

	public double getLearningRate() {
		return learningRate;
	}
}
