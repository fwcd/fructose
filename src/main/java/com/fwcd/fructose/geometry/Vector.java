package com.fwcd.fructose.geometry;

import java.io.Serializable;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import com.fwcd.fructose.exception.SizeMismatchException;
import com.fwcd.fructose.math.Tensor;

/**
 * An immutable, n-element vector.
 * 
 * @author Fredrik
 *
 */
public class Vector implements Serializable {
	private static final long serialVersionUID = -6990352776476335167L;
	private final double[] values;
	
	public Vector(double... values) {
		this.values = values;
	}
	
	public double get(int i) {
		return values[i];
	}
	
	public int size() {
		return values.length;
	}
	
	public double length() {
		double sqSum = 0;
		
		for (double v : values) {
			sqSum += v * v;
		}
		
		return Math.sqrt(sqSum);
	}
	
	public Vector add(Vector other) {
		return combine(other, (a, b) -> a + b);
	}
	
	public Vector sub(Vector other) {
		return combine(other, (a, b) -> a - b);
	}
	
	public Vector scale(double factor) {
		return map(v -> v * factor);
	}
	
	public Vector normalize() {
		double length = length();
		return map(v -> v / length);
	}
	
	public Vector map(DoubleUnaryOperator mapper) {
		double[] result = new double[size()];
		
		for (int i=0; i<size(); i++) {
			result[i] = mapper.applyAsDouble(result[i]);
		}
		
		return new Vector(result);
	}
	
	public Vector combine(Vector other, DoubleBinaryOperator operator) {
		if (size() != other.size()) {
			throw new SizeMismatchException("vector size", size(), "vector size", other.size());
		}
		
		double[] result = new double[size()];
		
		for (int i=0; i<size(); i++) {
			result[i] = operator.applyAsDouble(values[i], other.values[i]);
		}
		
		return new Vector(result);
	}
	
	public double dot(Vector other) {
		if (size() != other.size()) {
			throw new SizeMismatchException("vector size", size(), "vector size", other.size());
		}
		
		double result = 0;
		
		for (int i=0; i<size(); i++) {
			result += values[i] * other.values[i];
		}
		
		return result;
	}
	
	public Matrix asMatrix() {
		double[][] result = new double[size()][1];
		
		for (int i=0; i<size(); i++) {
			result[i][0] = values[i];
		}
		
		return new Matrix(result);
	}
	
	public Tensor asTensor() {
		return new Tensor(values);
	}
	
	public Vector2D asVector2D() {
		if (size() != 2) {
			throw new SizeMismatchException("vector size", size(), "vector size", 2);
		}
		
		return new Vector2D(values[0], values[1]);
	}
	
	public Vector3D asVector3D() {
		if (size() != 3) {
			throw new SizeMismatchException("vector size", size(), "vector size", 3);
		}
		
		return new Vector3D(values[0], values[1], values[2]);
	}
}
