package com.fwcd.fructose.ml.neural;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.fwcd.fructose.exception.SerializationException;
import com.fwcd.fructose.exception.SizeMismatchException;

/**
 * A basic multi-layer perceptron that
 * focuses on performance and is intended to
 * be trained through a genetic algorithm. If flexibility is desired,
 * {@link Perceptron} might be a better choice.
 * 
 * @author Fredrik
 *
 */
public class GeneticMLP implements NeuralNetwork<float[], float[]> {
	private static final long serialVersionUID = 2034199368901910617L;
	
	private final int[] layerSizes; // Contains amount of neurons in each layer
	private float[] weights; // Contains weight matrices
	
	public GeneticMLP(int... layerSizes) {
		this.layerSizes = layerSizes;
		
		int weightsCount = 0;
		
		for (int i=1; i<layerSizes.length; i++) {
			weightsCount += (layerSizes[i - 1] + 1) * layerSizes[i];
		}
		
		weights = new float[weightsCount];
	}
	
	public void saveWeights(File file) {
		try (FileOutputStream fos = new FileOutputStream(file); DataOutputStream dos = new DataOutputStream(fos)) {
			// First serialize array length
			dos.writeInt(weights.length);
			
			// Then serialize values
			for (float weight : weights) {
				dos.writeFloat(weight);
			}
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}
	
	public void loadWeights(File file) {
		try (FileInputStream fis = new FileInputStream(file); DataInputStream dis = new DataInputStream(fis)) {
			// First read array length
			weights = new float[dis.readInt()];
			
			// Then deserialize values
			int i = 0;
			while (dis.available() > 0) {
				weights[i++] = dis.readFloat();
			}
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}
	
	@Override
	public float[] compute(float[] input) {
		if (input.length != layerSizes[0]) {
			throw new SizeMismatchException("input vector length", input.length, "first layer size", layerSizes[0]);
		}
		
		int weightIndex = 0;
		float[] layer = input;
		
		for (int layerI=1; layerI<layerSizes.length; layerI++) {
			float[] nextLayer = new float[layerSizes[layerI]];
			
			for (int i=0; i<nextLayer.length; i++) {
				float dot = 0;
				
				for (float neuron : layer) {
					dot += neuron * weights[weightIndex++];
				}
				
				float bias = weights[weightIndex++];
				nextLayer[i] = relu(dot + bias);
			}
			
			layer = nextLayer;
		}
		
		return layer;
	}
	
	private float relu(float x) {
		return x >= 0 ? x : 0;
	}
}
