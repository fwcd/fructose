package com.fredrikw.fructose.ml.math;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import com.fredrikw.fructose.ArrayIterator;
import com.fredrikw.fructose.function.FloatSupplier;
import com.fredrikw.fructose.function.FloatUnaryOperator;
import com.fredrikw.fructose.geometry.Vector2D;
import com.fredrikw.fructose.ml.exception.SizeMismatchException;

/**
 * A mutable float matrix specifically designed
 * for neural networks with performance in mind.
 * 
 * @author Fredrik
 *
 */
public class NNMatrix implements Iterable<float[]> {
	private final float[][] data;
	
	public NNMatrix(int width, int height) {
		data = new float[height][width];
	}
	
	public NNMatrix(float[][] data) {
		this.data = data;
	}
	
	public void fill(FloatSupplier generator) {
		for (int y=0; y<height(); y++) {
			for (int x=0; x<width(); x++) {
				data[y][x] = generator.getAsFloat();
			}
		}
	}
	
	public NNMatrix transpose() {
		int width = width();
		int height = height();
		NNMatrix result = new NNMatrix(height, width);
		
		for (int y=0; y<result.height(); y++) {
			for (int x=0; x<result.width(); x++) {
				result.data[y][x] = data[x][y];
			}
		}
		
		return result;
	}
	
	public NNVector hadamardProduct(NNVector other) {
		if (width() != 1 || height() != other.size()) {
			throw new SizeMismatchException("matrix size", getSize(), "vector size", other.size());
		}
		
		NNVector result = new NNVector(other.size());
		
		for (int i=0; i<result.size(); i++) {
			result.set(i, data[i][0] * other.get(i));
		}
		
		return result;
	}
	
	public NNMatrix hadamardProduct(NNMatrix other) {
		int width = width();
		int height = height();
		
		if (width != other.width() || height != other.height()) {
			throw new SizeMismatchException("first matrix size", getSize(), "second matrix size", other.getSize());
		}
		
		NNMatrix result = new NNMatrix(width, height);
		
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				result.data[y][x] = data[y][x] * other.data[y][x];
			}
		}
		
		return result;
	}
	
	public void fillRandomly() {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		for (int y=0; y<height(); y++) {
			for (int x=0; x<width(); x++) {
				data[y][x] = rand.nextFloat();
			}
		}
	}

	public int width() {
		return data[0].length;
	}
	
	public int height() {
		return data.length;
	}
	
	public void incrementByAll(Iterable<NNMatrix> deltas) {
		for (NNMatrix delta : deltas) {
			increment(delta);
		}
	}
	
	public void increment(NNMatrix delta) {
		if (delta.width() != width() || delta.height() != height()) {
			throw new SizeMismatchException("delta matrix size", delta.getSize(), "matrix size", getSize());
		}
		
		for (int y=0; y<height(); y++) {
			for (int x=0; x<width(); x++) {
				data[y][x] += delta.data[y][x];
			}
		}
	}
	
	public void mapDirectly(FloatUnaryOperator func) {
		for (int y=0; y<height(); y++) {
			for (int x=0; x<width(); x++) {
				data[y][x] = func.applyAsFloat(data[y][x]);
			}
		}
	}
	
	public NNMatrix multiply(float scalar) {
		int width = width();
		int height = height();
		NNMatrix result = new NNMatrix(width, height);
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				result.data[y][x] = data[y][x] * scalar;
			}
		}
		
		return result;
	}
	
	public NNVector multiply(NNVector vec) {
		if (vec.size() != width()) {
			throw new SizeMismatchException("vector size", vec.size(), "matrix width", width());
		}
		
		NNVector result = new NNVector(height());
		
		for (int y=0; y<height(); y++) {
			for (int x=0; x<width(); x++) {
				result.increment(y, data[y][x] * vec.get(x));
			}
		}
		
		return result;
	}
	
	public NNMatrix multiply(NNMatrix right) {
		int width = width();
		
		if (right.height() != width) {
			throw new SizeMismatchException("left matrix width", width, "right matrix height", right.height());
		}
		
		NNMatrix result = new NNMatrix(right.width(), height());
		
		for (int y=0; y<result.height(); y++) {
			for (int x=0; x<result.width(); x++) {
				float dot = 0;
				
				for (int i=0; i<width; i++) {
					dot += get(i, y) * right.get(x, i);
				}
				
				result.set(x, y, dot);
			}
		}
		
		return result;
	}
	
	public void increment(int x, int y, float value) {
		data[y][x] += value;
	}
	
	public Vector2D getSize() {
		return new Vector2D(width(), height());
	}
	
	public NNVector asVector() {
		if (width() == 1) {
			int height = height();
			NNVector result = new NNVector(height);
			
			for (int i=0; i<height; i++) {
				result.set(i, data[i][0]);
			}
			
			return result;
		} else {
			throw new SizeMismatchException("matrix width", width(), "required vector width", 1);
		}
	}
	
	public void set(int x, int y, float value) {
		data[y][x] = value;
	}
	
	public float get(int x, int y) {
		return data[y][x];
	}
	
	public float[] getRow(int y) {
		float[] row = data[y];
		return Arrays.copyOf(row, row.length);
	}
	
	public float[] getCol(int x) {
		float[] col = new float[height()];
		
		for (int y=0; y<height(); y++) {
			col[y] = data[y][x];
		}
		
		return col;
	}
	
	@Override
	public Iterator<float[]> iterator() {
		return new ArrayIterator<>(data);
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		
		for (int y=0; y<height(); y++) {
			b.append(Arrays.toString(data[y]));
			b.append("\n");
		}
		
		return b.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(data);
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
		NNMatrix other = (NNMatrix) obj;
		if (!Arrays.deepEquals(data, other.data)) {
			return false;
		}
		return true;
	}
}
