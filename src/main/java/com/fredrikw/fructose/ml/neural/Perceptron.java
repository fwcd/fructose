package com.fredrikw.fructose.ml.neural;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.fredrikw.fructose.function.FloatSupplier;
import com.fredrikw.fructose.ml.data.LabelledData;
import com.fredrikw.fructose.ml.exception.SizeMismatchException;
import com.fredrikw.fructose.ml.function.DiffFunction;
import com.fredrikw.fructose.ml.function.NNFunction;
import com.fredrikw.fructose.ml.function.NNWeightInit;
import com.fredrikw.fructose.ml.function.WeightInit;
import com.fredrikw.fructose.ml.math.NNMatrix;
import com.fredrikw.fructose.ml.math.NNVector;

/**
 * A flexible, multi-layer feed-forward perceptron.
 * Can be constructed through the nested builder.
 * 
 * @author Fredrik
 *
 */
@SuppressWarnings("serial")
public class Perceptron implements NeuralNetwork<NNVector, NNVector> {
	private final int[] layerSizes;
	private final NNMatrix[] allWeights;
	private final NNVector[] allBiases;
	private final DiffFunction activationFunction;
	private final WeightInit weightInit;
	private final float learnFactor;
	
	protected Perceptron(
			DiffFunction activationFunction,
			WeightInit weightInit,
			int[] layerSizes,
			float learnFactor
	) {
		this.weightInit = weightInit;
		this.activationFunction = activationFunction;
		this.layerSizes = layerSizes;
		this.learnFactor = learnFactor;
		
		allWeights = new NNMatrix[layerSizes.length];
		allBiases = new NNVector[layerSizes.length];
		
		reInitWeights();
	}

	public void reInitWeights() {
		// First layer does not have any input neurons,
		// so it doesn't have weights/biases either
		
		allWeights[0] = null;
		allBiases[0] = null;
		
		for (int i=1; i<layerSizes.length; i++) {
			final int inputNeurons = layerSizes[i - 1];
			final int outputNeurons = layerSizes[i];
			final FloatSupplier generator = () -> weightInit.getWeight(inputNeurons, outputNeurons);
			
			NNMatrix weights = new NNMatrix(inputNeurons, outputNeurons);
			weights.fill(generator);
			allWeights[i] = weights;
			
			NNVector biases = new NNVector(outputNeurons);
			biases.fill(generator);
			allBiases[i] = biases;
		}
	}
	
	public float cost(LabelledData<NNVector, NNVector> data) {
		return (float) data.getInputs().stream()
				.mapToDouble(in -> cost(in, data.getOutput(in)))
				.average()
				.orElseThrow(NoSuchElementException::new);
	}
	
	public float cost(NNVector input, NNVector targetOut) {
		if (targetOut.size() != layerSizes[layerCount() - 1]) {
			throw new SizeMismatchException("target out vector's size", targetOut.size(), "last layer neurons", layerSizes[layerCount() - 1]);
		}
		
		NNVector output = compute(input); 
		float result = 0;
		
		for (int i=0; i<targetOut.size(); i++) {
			result += Math.pow(output.get(i) - targetOut.get(i), 2);
		}
		
		return result;
	}
	
	public NNMatrix getWeights(int layerIndex) {
		if (layerIndex <= 0) {
			throw new IllegalArgumentException("Invalid layer index: " + Integer.toString(layerIndex));
		}
		
		return allWeights[layerIndex];
	}
	
	public NNVector getBiases(int layerIndex) {
		if (layerIndex <= 0) {
			throw new IllegalArgumentException("Invalid layer index: " + Integer.toString(layerIndex));
		}
		
		return allBiases[layerIndex];
	}
	
	@Override
	public NNVector compute(NNVector in) {
		if (in.size() != layerSizes[0]) {
			throw new SizeMismatchException("input vector size", in.size(), "first layer neurons", layerSizes[0]);
		}
		
		final int layerCount = layerCount();
		NNVector result = in;
		
		for (int i=1; i<layerCount; i++) {
			result = feedForward(i, result);
		}
		
		return result;
	}

	private NNVector weightedSum(int targetLayer, NNVector in) {
		return getWeights(targetLayer).multiply(in).add(getBiases(targetLayer));
	}
	
	private NNVector feedBackward(NNVector activatedLayer, NNVector nonActivatedLayer) {
		if (activationFunction.canComputeDfUsingF()) {
			return activatedLayer.map(activationFunction::dfUsingF);
		} else {
			return nonActivatedLayer.map(activationFunction::df);
		}
	}
	
	private NNVector feedForward(NNVector nonActivatedLayer, int targetLayer, NNVector in) {
		return nonActivatedLayer.map(activationFunction::f);
	}
	
	private NNVector feedForward(int targetLayer, NNVector in) {
		return feedForward(weightedSum(targetLayer, in), targetLayer, in);
	}

	@Override
	public void backprop(LabelledData<NNVector, NNVector> data) {
		final int layerCount = layerCount();
		int samples = 0;
		NNMatrix[] sumWeightNudges = new NNMatrix[layerCount];
		NNVector[] sumBiasNudges = new NNVector[layerCount];
		NNMatrix[] currentWeightNudges = new NNMatrix[layerCount];
		NNVector[] currentBiasNudges = new NNVector[layerCount];
		NNVector[] activatedLayers = new NNVector[layerCount];
		NNVector[] nonActivatedLayers = new NNVector[layerCount];
		
		for (NNVector input : data.getInputs()) {
			NNVector targetOutput = data.getOutput(input);
			
			// Forwardpass
			
			NNVector result = input;
			
			nonActivatedLayers[0] = null;
			activatedLayers[0] = input;
			
			for (int i=1; i<layerCount; i++) {
				NNVector nonActivated = weightedSum(i, result);
				nonActivatedLayers[i] = nonActivated;
				
				result = feedForward(nonActivated, i, result);
				activatedLayers[i] = result;
			}
			
			// Backwardpass

			int lastLayerIndex = layerCount - 1;
			backprop(
					activatedLayers,
					nonActivatedLayers,
					lastLayerIndex,
					currentWeightNudges,
					currentBiasNudges,
					result.sub(targetOutput)
							.hadamardProduct(feedBackward(activatedLayers[lastLayerIndex], nonActivatedLayers[lastLayerIndex]))
			);
			
			for (int i=0; i<layerCount; i++) {
				if (sumWeightNudges[i] == null) {
					sumWeightNudges[i] = currentWeightNudges[i];
				} else {
					sumWeightNudges[i].increment(currentWeightNudges[i]);
				}
				if (sumBiasNudges[i] == null) {
					sumBiasNudges[i] = currentBiasNudges[i];
				} else {
					sumBiasNudges[i].increment(currentBiasNudges[i]);
				}
			}
			
			samples++;
		}

		// Apply averaged nudges to current weights and biases
		
		int wLayerIndex = 0;
		for (NNMatrix sumWeightsDelta : sumWeightNudges) {
			if (sumWeightsDelta != null) {
				NNMatrix weights = allWeights[wLayerIndex];
				weights.increment(sumWeightsDelta.multiply(1F / samples));
			}
			wLayerIndex++;
		}
		
		int bLayerIndex = 0;
		for (NNVector sumBiasDelta : sumBiasNudges) {
			if (sumBiasDelta != null) {
				NNVector biases = allBiases[bLayerIndex];
				biases.increment(sumBiasDelta.multiply(1F / samples));
			}
			bLayerIndex++;
		}
	}
	
	private void backprop(
			NNVector[] activatedLayers,
			NNVector[] nonActivatedLayers,
			int layer,
			NNMatrix[] weightNudges,
			NNVector[] biasNudges,
			NNVector kroneckerDelta
	) {
		weightNudges[layer] = kroneckerDelta.multiply(-learnFactor).multiply(activatedLayers[layer - 1].transpose());
		biasNudges[layer] = kroneckerDelta.multiply(-learnFactor);
		
		if (layer > 1) {
			backprop(
					activatedLayers,
					nonActivatedLayers,
					layer - 1,
					weightNudges,
					biasNudges,
					allWeights[layer].transpose().multiply(kroneckerDelta)
							.hadamardProduct(feedBackward(activatedLayers[layer - 1], nonActivatedLayers[layer - 1]))
			);
		}
	}
	
	public int layerCount() {
		return layerSizes.length;
	}
	
	public static class Builder implements NNBuilder<Perceptron> {
		private final List<Integer> layers = new ArrayList<>();
		private DiffFunction activationFunction = NNFunction.LEAKY_RELU;
		private WeightInit weightInit = NNWeightInit.XAVIER;
		private float learnFactor = 0.1F;
		
		public Builder layer(int size) {
			layers.add(size);
			return this;
		}
		
		public Builder learnFactor(float learnFactor) {
			this.learnFactor  = learnFactor;
			return this;
		}
		
		public Builder activationFunc(DiffFunction activationFunction) {
			this.activationFunction = activationFunction;
			return this;
		}
		
		public Builder weightInit(WeightInit weightInit) {
			this.weightInit = weightInit;
			return this;
		}
		
		@Override
		public Perceptron build() {
			return new Perceptron(
					activationFunction,
					weightInit,
					layers.stream().mapToInt(Integer::valueOf).toArray(),
					learnFactor
			);
		}
	}
}
