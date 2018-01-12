package com.fredrikw.fructose.ml.neural;

public interface NeuralNetwork<I, O> {
	O compute(I input);
	
	void backprop(LabelledData<I, O> data);
}
