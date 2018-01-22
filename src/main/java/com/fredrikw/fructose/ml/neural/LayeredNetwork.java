package com.fredrikw.fructose.ml.neural;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fredrikw.fructose.ml.data.LabelledData;
import com.fredrikw.fructose.ml.neural.layers.BackpropResult;
import com.fredrikw.fructose.ml.neural.layers.NNLayer;
import com.fredrikw.fructose.structs.ArrayStack;
import com.fredrikw.fructose.structs.Stack;

/**
 * <p>A layered neural network that generalizes common
 * architectures, including Convolutional Neural Network's
 * and Perceptron's (both of these can be created using
 * this class).</p>
 * 
 * <p>Construction is done through the nested builder
 * (which requires at minimum an input layer and an output
 * layer):</p>
 * 
 * <p>{@code new LayeredNetwork.Builder()}</p>
 * 
 * @author Fredrik
 *
 * @param <I> - The input data type
 * @param <O> - The output data type
 */
public class LayeredNetwork<I, O> implements NeuralNetwork<I, O> {
	private static final long serialVersionUID = -5634042575520289180L;
	private final Hyperparameters hyperparameters;
	private final NNLayer<I, ?, ?> inLayer;
	private final List<NNLayer<?, ?, ?>> hiddenLayers;
	private final NNLayer<?, O, ?> outLayer;
	
	private LayeredNetwork(
			NNLayer<I, ?, ?> inLayer,
			List<NNLayer<?, ?, ?>> hiddenLayers,
			NNLayer<?, O, ?> outLayer,
			Hyperparameters hyperparameters
	) {
		this.inLayer = inLayer;
		this.hiddenLayers = hiddenLayers;
		this.outLayer = outLayer;
		this.hyperparameters = hyperparameters;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public O compute(I input) {
		Object lastOut = inLayer.forwardprop(input);
		for (NNLayer<?, ?, ?> layer : hiddenLayers) {
			((NNLayer<Object, Object, ?>) layer).forwardprop(lastOut);
		}
		return (O) lastOut;
	}

	@Override
	public void backprop(LabelledData<I, O> data) {
		// TODO
	}
	
	private void backprop(int index, NNLayer<?, ?, ?> layer) {
		// TODO
	}
	
	public NNLayer<I, ?, ?> getInLayer() {
		return inLayer;
	}
	
	public NNLayer<?, O, ?> getOutLayer() {
		return outLayer;
	}
	
	// ==================
	// ==== BUILDERS ====
	// ==================
	
	/**
	 * First stage of construction. Specification
	 * of hyperparameters and insertion of the first layer.
	 * 
	 * <p>(The advantage of using [multiple] builders is type-safety,
	 * fool-proofness, as well as readability and convenience when using the API)</p>
	 */
	public static class Builder {
		private double learningRate = 0.1D;
		
		public Builder learningRate(double learningRate) {
			this.learningRate = learningRate;
			return this;
		}
		
		public <I> LayerStackBuilder<I, I> inLayer(NNLayer<I, ?, ?> inLayer) {
			return new LayerStackBuilder<>(this, Objects.requireNonNull(inLayer));
		}
	}
	
	/**
	 * Second stage of construction. Insertion of all
	 * "hidden" layers and the output layer.
	 */
	public static class LayerStackBuilder<I, N> {
		private final Builder firstBuilder;
		private final NNLayer<I, ?, ?> inLayer;
		private final List<NNLayer<?, ?, ?>> hiddenLayers = new ArrayList<>();
		
		private LayerStackBuilder(Builder firstBuilder, NNLayer<I, ?, ?> inLayer) {
			this.firstBuilder = firstBuilder;
			this.inLayer = inLayer;
		}
		
		@SuppressWarnings("unchecked")
		public <M> LayerStackBuilder<I, M> layer(NNLayer<N, M, ?> layer) {
			hiddenLayers.add(layer);
			// This is more or less a hack of the type-system to ensure
			// that all hidden layers have the correct in and out types.
			// If runtimed generics were implemented this would probably require
			// a new instance of the layer stack builder to be created,
			// but as of Java 8 this is not the case yet.
			return (LayerStackBuilder<I, M>) this;
		}
		
		public <O> ResultBuilder<I, O> outLayer(NNLayer<N, O, ?> layer) {
			return new ResultBuilder<>(this, Objects.requireNonNull(layer));
		}
	}
	
	/**
	 * Final stage of construction. Assembles the actual network.
	 */
	public static class ResultBuilder<I, O> implements NNBuilder<LayeredNetwork<I, O>> {
		private final Builder firstBuilder;
		private final LayerStackBuilder<I, ?> layerBuilder;
		private final NNLayer<?, O, ?> outLayer;
		
		private ResultBuilder(LayerStackBuilder<I, ?> layerBuilder, NNLayer<?, O, ?> outLayer) {
			firstBuilder = layerBuilder.firstBuilder;
			this.layerBuilder = layerBuilder;
			this.outLayer = outLayer;
		}
		
		private Hyperparameters compileHyperparameters() {
			return new Hyperparameters(
					firstBuilder.learningRate
			);
		}
		
		@Override
		public LayeredNetwork<I, O> build() {
			return new LayeredNetwork<>(
					layerBuilder.inLayer,
					layerBuilder.hiddenLayers,
					outLayer,
					compileHyperparameters()
			);
		}
	}
}
