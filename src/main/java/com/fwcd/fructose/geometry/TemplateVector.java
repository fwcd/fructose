package com.fwcd.fructose.geometry;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import com.fwcd.fructose.exception.SizeMismatchException;
import com.fwcd.fructose.math.DoubleTensor;
import com.fwcd.fructose.operations.Addable;
import com.fwcd.fructose.operations.Subtractable;
import com.fwcd.fructose.operations.ToleranceEquatable;

/**
 * Provides high-performance template implementations for
 * immutable, double-valued vectors to
 * reduce redundancies amongst specializations.
 * 
 * @author Fredrik
 *
 * @param <V> - The sub type
 */
public abstract class TemplateVector<V extends TemplateVector<V>> implements
		Addable<V, V>,
		Subtractable<V, V>,
		ToleranceEquatable<V> {
	private final double[] data;
	
	public TemplateVector(double... data) {
		this.data = data;
	}
	
	public TemplateVector(boolean predicate, RuntimeException ifWrong, double... data) {
		if (predicate) {
			this.data = data;
		} else {
			throw ifWrong;
		}
	}
	
	protected abstract V newInstance(double... data);
	
	protected double get(int i) {
		return data[i];
	}
	
	public int size() {
		return data.length;
	}
	
	/**
	 * Performs elementwise addition.
	 * 
	 * @param other - The other vector
	 * @return this + other
	 */
	@Override
	public V add(V other) {
		final int size = size();
		assertEqualSize(size, other.size());
		double[] res = new double[size];
		
		for (int i=0; i<size; i++) {
			res[i] = get(i) + other.get(i);
		}
		
		return newInstance(res);
	}
	
	/**
	 * Performs elementwise subtraction.
	 * 
	 * @param other - The other vector
	 * @return this - other
	 */
	@Override
	public V sub(V other) {
		final int size = size();
		assertEqualSize(size, other.size());
		double[] res = new double[size];
		
		for (int i=0; i<size; i++) {
			res[i] = get(i) - other.get(i);
		}
		
		return newInstance(res);
	}
	
	/**
	 * Performs elementwise multiplication.
	 * 
	 * @param factor - A scalar factor
	 * @return this * factor
	 */
	public V scale(double factor) {
		final int size = size();
		double[] res = new double[size];
		
		for (int i=0; i<size; i++) {
			res[i] = get(i) * factor;
		}
		
		return newInstance(res);
	}
	
	/**
	 * Performs elementwise Math.min().
	 * 
	 * @param other - The other vector
	 * @return min(this)
	 */
	public V min(V other) {
		final int size = size();
		assertEqualSize(size, other.size());
		double[] res = new double[size];
		
		for (int i=0; i<size; i++) {
			res[i] = Math.min(get(i), other.get(i));
		}
		
		return newInstance(res);
	}
	
	/**
	 * Performs elementwise Math.max().
	 * 
	 * @param other - The other vector
	 * @return max(this)
	 */
	public V max(V other) {
		final int size = size();
		assertEqualSize(size, other.size());
		double[] res = new double[size];
		
		for (int i=0; i<size; i++) {
			res[i] = Math.max(get(i), other.get(i));
		}
		
		return newInstance(res);
	}
	
	/**
	 * Performs elementwise multiplication.
	 * 
	 * @param other - The other vector
	 * @return this o other
	 */
	public V hadamardProduct(V other) {
		final int size = size();
		assertEqualSize(size, other.size());
		double[] res = new double[size];
		
		for (int i=0; i<size; i++) {
			res[i] = get(i) * other.get(i);
		}
		
		return newInstance(res);
	}
	
	/**
	 * Returns the absolute value or length of this vector.
	 * 
	 * @param other - The other vector
	 * @return abs(this)
	 */
	public double length() {
		final int size = size();
		double squaredSum = 0;
		
		for (int i=0; i<size; i++) {
			final double component = data[i];
			squaredSum += component * component;
		}
		
		return Math.sqrt(squaredSum);
	}
	
	/**
	 * Normalizes this vector to the length 1.
	 * 
	 * @return A unit vector pointing in the current direction
	 */
	public V normalize() {
		final int size = size();
		final double length = length();
		double[] result = new double[size];
		
		for (int i=0; i<size; i++) {
			result[i] = get(i) / length;
		}
		
		return newInstance(result);
	}
	
	/**
	 * Performs elementwise inversion.
	 * 
	 * @return -this
	 */
	public V invert() {
		final int size = size();
		double[] result = new double[size];
		
		for (int i=0; i<size; i++) {
			result[i] = -get(i);
		}
		
		return newInstance(result);
	}
	
	/**
	 * Dots this vector against another.
	 * 
	 * @param other - The other vector
	 * @return this * other
	 */
	public double dot(V other) {
		final int size = size();
		assertEqualSize(size, other.size());
		double dot = 0;
		
		for (int i=0; i<size; i++) {
			dot += get(i) * other.get(i);
		}
		
		return dot;
	}
	
	public V map(DoubleUnaryOperator mapper) {
		final int size = size();
		double[] res = new double[size];
		
		for (int i=0; i<size; i++) {
			res[i] = mapper.applyAsDouble(get(i));
		}
		
		return newInstance(res);
	}
	
	public V combine(V other, DoubleBinaryOperator combiner) {
		final int size = size();
		assertEqualSize(size, other.size());
		double[] res = new double[size];
		
		for (int i=0; i<size; i++) {
			res[i] = combiner.applyAsDouble(get(i), other.get(i));
		}
		
		return newInstance(res);
	}
	
	public double reduce(DoubleBinaryOperator associativeAccumulator) {
		final int size = size();
		double result = get(0);
		
		for (int i=1; i<size; i++) {
			result = associativeAccumulator.applyAsDouble(result, get(i));
		}
		
		return result;
	}
	
	/**
	 * @return A matrix representing this vector as a column vector
	 */
	public DoubleMatrix asMatrix() {
		final int size = size();
		double[][] result = new double[size][1];
		
		for (int i=0; i<size; i++) {
			result[i][0] = get(i);
		}
		
		return new DoubleMatrix(result);
	}
	
	/**
	 * @return A tensor representing this vector
	 */
	public DoubleTensor asTensor() {
		return new DoubleTensor(data);
	}

	private void assertEqualSize(int size, int otherSize) {
		if (size != otherSize) {
			throw new SizeMismatchException("vector size", size, "vector size", otherSize);
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
	public boolean equals(V other, double tolerance) {
		final int size = size();
		if (size != other.size()) {
			return false;
		}
		
		for (int i=0; i<size; i++) {
			if (Math.abs(get(i) - other.get(i)) > tolerance) {
				return false;
			}
		}
		
		return true;
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
		TemplateVector<?> other = (TemplateVector<?>) obj;
		if (!Arrays.equals(data, other.data)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return Arrays.toString(data);
	}
}
