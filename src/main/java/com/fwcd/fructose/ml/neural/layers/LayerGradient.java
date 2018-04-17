package com.fwcd.fructose.ml.neural.layers;

import com.fwcd.fructose.annotation.WIP;

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
@WIP(usable = false)
public interface LayerGradient<L extends NNLayer<?, ?, ?>> {
	void apply(L layer);
}
