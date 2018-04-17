package com.fwcd.fructose.ml.neural;

import java.io.Serializable;

/**
 * An artifical neural network/general function approximator
 * that can be trained using supervised learning.
 * 
 * @author Fredrik
 *
 * @param <I> - The input data type
 * @param <O> - The output data type
 */
public interface NeuralNetwork<I, O> extends Serializable {
	O compute(I input);
}
