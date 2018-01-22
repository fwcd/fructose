package com.fredrikw.fructose.ml.neural.layers;

import com.fredrikw.fructose.ml.neural.Hyperparameters;
import com.fredrikw.fructose.ml.neural.LayeredNetwork;

/**
 * A single layer in a {@link LayeredNetwork}.
 * 
 * @author Fredrik
 *
 * @param <I> - The input data type
 * @param <O> - The output data type
 * @param <S> - The subtype/implementation type
 */
public interface NNLayer<I, O, S extends NNLayer<I, O, S>> {
	/**
	 * Forwardpropagates the input through this layer.
	 * 
	 * @param input - The input
	 * @return The output
	 */
	O forwardprop(I input);
	
	/**
	 * Backpropagates the error through this layer.
	 * 
	 * @param input - The input
	 * @param error - The partial derivative with respect to this layer
	 * @param hyp - The hyperparameters to be used (e.g. including the learning rate)
	 * @return The gradient of this layer and the previous' layer's error parameter
	 */
	BackpropResult<I, S> backprop(I input, O error, Hyperparameters hyp);
}
