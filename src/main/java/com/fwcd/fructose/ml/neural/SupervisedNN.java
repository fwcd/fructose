package com.fwcd.fructose.ml.neural;

import java.util.Map;

import com.fwcd.fructose.ml.data.LabelledData;
import com.fwcd.fructose.ml.function.LearningFunction;

/**
 * A neural network that can be trained using
 * supervised learning.
 * 
 * @author Fredrik
 *
 * @param <I> - The input data type
 * @param <O> - The output data type
 */
public interface SupervisedNN<I, O> extends NeuralNetwork<I, O>, LearningFunction<I, O> {
	void backprop(LabelledData<I, O> data);

	@Override
	default void teach(Map<I, O> examples) {
		backprop(new LabelledData<>(examples));
	}
}
