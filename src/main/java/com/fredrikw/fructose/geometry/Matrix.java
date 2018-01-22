package com.fredrikw.fructose.geometry;

import java.util.Arrays;
import java.util.Iterator;

import com.fredrikw.fructose.ArrayIterator;

/**
 * An immutable "flat" matrix. (A rank 2-tensor)
 * 
 * @author Fredrik
 *
 */
public class Matrix implements Iterable<double[]> {
	private double[][] matrix;
	
	private Matrix(int width, int height) {
		matrix = new double[height][width];
	}
	
	public Matrix(double[][] contents) {
		matrix = contents;
	}
	
	public double get(int x, int y) {
		return matrix[y][x];
	}
	
	public Matrix multiply(Vector vector) {
		return multiply(vector.asMatrix());
	}
	
	public Matrix multiply(Vector2D vector) {
		return multiply(vector.asMatrix());
	}
	
	public Matrix multiply(Vector3D vector) {
		return multiply(vector.asMatrix());
	}
	
	/**
	 * Multiplies this matrix with another one.<br><br>
	 * 
	 * this * other
	 * 
	 * @param other
	 * @return MatA * MatB
	 */
	public Matrix multiply(Matrix other) {
		if (width() != other.height()) {
			throw new ArithmeticException("The width of this matrix need to equal the height of the other matrix.");
		}
		
		Matrix result = new Matrix(other.width(), height());
		
		for (int row=0; row<height(); row++) {
			for (int col=0; col<other.width(); col++) {
				double cell = 0;
				
				for (int i=0; i<matrix[row].length; i++) {
					cell += matrix[row][i] * other.matrix[i][col];
				}
				
				result.matrix[row][col] = cell;
			}
		}
		
		return result;
	}

	private int width() {
		return matrix[0].length;
	}

	private int height() {
		return matrix.length;
	}
	
	public int getRows() {
		return height();
	}
	
	public int getCols() {
		return width();
	}
	
	@Override
	public String toString() {
		String s = "[Matrix]\n";
		
		for (int y=0; y<height(); y++) {
			for (int x=0; x<width(); x++) {
				s += matrix[y][x] + " ";
			}
			
			s += "\n";
		}
		
		return s;
	}

	@Override
	public Iterator<double[]> iterator() {
		return new ArrayIterator<>(matrix);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(matrix);
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
		Matrix other = (Matrix) obj;
		if (!Arrays.deepEquals(matrix, other.matrix)) {
			return false;
		}
		return true;
	}
}
