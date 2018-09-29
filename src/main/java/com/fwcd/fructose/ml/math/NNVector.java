package com.fwcd.fructose.ml.math;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import com.fwcd.fructose.exception.SizeMismatchException;
import com.fwcd.fructose.function.FloatBinaryOperator;
import com.fwcd.fructose.function.FloatSupplier;
import com.fwcd.fructose.function.FloatUnaryOperator;
import com.fwcd.fructose.function.IntToFloatFunction;

/**
 * A mutable float vector specifically designed
 * for neural networks with performance in mind.
 * 
 * @author Fredrik
 *
 */
public class NNVector implements Iterable<Float>, Serializable {
	private static final long serialVersionUID = 8045665146795616327L;
	private final float[] data;
	
	/**
	 * Creates a new vector of the given size and
	 * fills it with zeroes.
	 * 
	 * @param size - The size of the vector
	 */
	public NNVector(int size) {
		data = new float[size];
		
		for (int i=0; i<size; i++) {
			data[i] = 0;
		}
	}
	
	public NNVector(int size, IntToFloatFunction generator) {
		data = new float[size];
		
		for (int i=0; i<size; i++) {
			data[i] = generator.applyAsInt(i);
		}
	}
	
	public NNVector(float... data) {
		this.data = data;
	}
	
	public void addInPlace(NNVector delta) {
		if (delta.size() != size()) {
			throw new SizeMismatchException("first summand's size", size(), "second summand's size", delta.size());
		}
		
		for (int i=0; i<size(); i++) {
			data[i] += delta.data[i];
		}
	}
	
	public NNVector add(NNVector vector) {
		if (vector.size() != size()) {
			throw new SizeMismatchException("first summand's size", size(), "second summand's size", vector.size());
		}
		
		float[] result = new float[size()];
		
		for (int i=0; i<size(); i++) {
			result[i] = data[i] + vector.data[i];
		}
		
		return new NNVector(result);
	}

	public NNVector sub(NNVector vector) {
		if (vector.size() != size()) {
			throw new SizeMismatchException("first summand's size", size(), "second summand's size", vector.size());
		}
		
		float[] result = new float[size()];
		
		for (int i=0; i<result.length; i++) {
			result[i] = data[i] - vector.data[i];
		}
		
		return new NNVector(result);
	}
	
	public NNMatrix transpose() {
		NNMatrix result = new NNMatrix(size(), 1);
		
		for (int i=0; i<data.length; i++) {
			result.set(i, 0, data[i]);
		}
		
		return result;
	}
	
	public NNVector zip(NNVector other, FloatBinaryOperator zipper) {
		if (size() != other.size()) {
			throw new SizeMismatchException("vector size", size(), "other vector size", other.size());
		}
		
		float[] result = new float[size()];
		
		for (int i=0; i<result.length; i++) {
			result[i] = zipper.applyAsFloat(data[i], other.data[i]);
		}
		
		return new NNVector(result);
	}
	
	public NNVector softmax() {
		float denom = 0;
		
		for (float value : data) {
			denom += Math.exp(value);
		}
		
		NNVector result = new NNVector(size());
		
		int i = 0;
		for (float value : data) {
			result.data[i] = (float) (Math.exp(value) / denom);
			i++;
		}
		
		return result;
	}
	
	public float maxValue() {
		if (size() == 0) {
			throw new UnsupportedOperationException("Can't max a vector of length 0!");
		}
		
		float max = Float.NEGATIVE_INFINITY;
		
		for (float v : data) {
			max = Math.max(v, max);
		}
		
		return max;
	}
	
	public int maxValueIndex() {
		if (size() == 0) {
			throw new UnsupportedOperationException("Can't max a vector of length 0!");
		}
		
		float max = Float.NEGATIVE_INFINITY;
		int maxI = -1;
		
		int i = 0;
		for (float v : data) {
			if (v > max) {
				max = v;
				maxI = i;
			}
			i++;
		}
		
		return maxI;
	}
	
	public NNVector hadamardProduct(NNVector vector) {
		if (vector.size() != size()) {
			System.err.println(this + " vs " + vector);
			throw new SizeMismatchException("first factor's size", size(), "second factor's size", vector.size());
		}
		
		float[] result = new float[size()];
		
		for (int i=0; i<size(); i++) {
			result[i] = data[i] * vector.data[i];
		}
		
		return new NNVector(result);
	}
	
	public void mapInPlace(FloatUnaryOperator func) {
		for (int i=0; i<size(); i++) {
			data[i] = func.applyAsFloat(data[i]);
		}
	}
	
	public NNVector map(FloatUnaryOperator func) {
		NNVector result = copy();
		result.mapInPlace(func);
		return result;
	}
	
	public float reduce(FloatBinaryOperator associativeReducer) {
		float result = 0;
		
		for (float item : data) {
			result = associativeReducer.applyAsFloat(result, item);
		}
		
		return result;
	}
	
	public NNMatrix multiply(NNMatrix other) {
		return asMatrix().multiply(other);
	}
	
	public NNMatrix asMatrix() {
		NNMatrix result = new NNMatrix(1, size());
		
		for (int i=0; i<data.length; i++) {
			result.set(0, i, data[i]);
		}
		
		return result;
	}
	
	public NNVector multiply(float scalar) {
		NNVector result = new NNVector(size());
		
		for (int i=0; i<size(); i++) {
			result.data[i] = data[i] * scalar;
		}
		
		return result;
	}
	
	public void fill(FloatSupplier generator) {
		for (int i=0; i<size(); i++) {
			data[i] = generator.getAsFloat();
		}
	}
	
	public void fillRandomly() {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		for (int i=0; i<size(); i++) {
			data[i] = rand.nextFloat();
		}
	}
	
	public void set(int i, float value) {
		data[i] = value;
	}
	
	public void addInPlace(int i, float value) {
		data[i] += value;
	}

	public NNVector invert() {
		NNVector result = new NNVector(size());
		
		for (int i=0; i<size(); i++) {
			result.data[i] = -data[i];
		}
		
		return result;
	}
	
	public float dot(float[] other) {
		if (size() != other.length) {
			throw new SizeMismatchException("first factor's size", size(), "second factor's size", other.length);
		}
		
		float result = 0;
		
		for (int i=0; i<size(); i++) {
			result += data[i] * other[i];
		}
		
		return result;
	}
	
	public float dot(NNVector other) {
		return dot(other.data);
	}
	
	public int size() {
		return data.length;
	}
	
	public float get(int i) {
		return data[i];
	}
	
	public NNVector copy() {
		return new NNVector(Arrays.copyOf(data, data.length));
	}

	public float[] asArray() {
		return data;
	}
	
	@Override
	public Iterator<Float> iterator() {
		return new Iterator<Float>() {
			private int i = 0;
			
			@Override
			public boolean hasNext() {
				return i < size();
			}

			@Override
			public Float next() {
				return data[i++];
			}
		};
	}
	
	@Override
	public String toString() {
		return "[NNVector] " + Arrays.toString(data);
	}

	public void addAllInPlace(Iterable<NNVector> deltas) {
		for (NNVector delta : deltas) {
			addInPlace(delta);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NNVector other = (NNVector) obj;
		if (!Arrays.equals(data, other.data)) {
			return false;
		}
		return true;
	}
}
