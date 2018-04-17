package com.fwcd.fructose.ml.neural.layers;

public class BackpropResult<I, L extends NNLayer<I, ?, ?>> {
	private final LayerGradient<L> gradient;
	private final I previousLayerError;
	
	public BackpropResult(LayerGradient<L> gradient, I previousLayerError) {
		this.gradient = gradient;
		this.previousLayerError = previousLayerError;
	}

	public LayerGradient<L> getGradient() {
		return gradient;
	}

	public I getPreviousLayerError() {
		return previousLayerError;
	}
}
