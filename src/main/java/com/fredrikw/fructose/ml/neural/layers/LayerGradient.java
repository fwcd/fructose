package com.fredrikw.fructose.ml.neural.layers;

/**
 * Contains the "gradient" of a layer (in case of a
 * dense layer (as in a perceptron) this would contain the
 * nudges to the weights and biases).
 * 
 * @author Fredrik
 *
 * @param <L> - The layer type
 */
@FunctionalInterface
public interface LayerGradient<L extends NNLayer<?, ?, ?>> {
	void apply(L layer);
}
