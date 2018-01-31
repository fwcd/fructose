package com.fwcd.fructose.ml.neural;

public class NNParameters {
	private final double learningRate;
	
	public NNParameters(
			double learningRate
	) {
		this.learningRate = learningRate;
	}

	public double getLearningRate() {
		return learningRate;
	}
}
