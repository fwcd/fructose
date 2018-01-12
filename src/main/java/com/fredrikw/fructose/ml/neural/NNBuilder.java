package com.fredrikw.fructose.ml.neural;

public interface NNBuilder<T extends NeuralNetwork<?, ?>> {
	T build();
}
