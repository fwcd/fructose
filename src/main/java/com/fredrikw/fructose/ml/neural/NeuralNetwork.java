package com.fredrikw.fructose.ml.neural;

import java.io.Serializable;

import com.fredrikw.fructose.ml.data.LabelledData;

public interface NeuralNetwork<I, O> extends Serializable {
	O compute(I input);
	
	void backprop(LabelledData<I, O> data);
}
