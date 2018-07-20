package com.fwcd.fructose.geometry;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.ToDoubleFunction;

import com.fwcd.fructose.ArrayIterator;
import com.fwcd.fructose.GridPos;
import com.fwcd.fructose.exception.SizeMismatchException;
import com.fwcd.fructose.math.DoubleTensor;
import com.fwcd.fructose.operations.Addable;
import com.fwcd.fructose.operations.Multipliable;
import com.fwcd.fructose.operations.Subtractable;
import com.fwcd.fructose.operations.ToleranceEquatable;

/**
 * An immutable, double-valued matrix. (A rank 2-tensor)
 */
public class DoubleMatrix implements
		Iterable<double[]>,
		Serializable,
		Addable<DoubleMatrix, DoubleMatrix>,
		Subtractable<DoubleMatrix, DoubleMatrix>,
		Multipliable<DoubleMatrix, DoubleMatrix>,
		ToleranceEquatable<DoubleMatrix> {
	private static final long serialVersionUID = 6699594319631613838L;
	private double[][] data;
	
	public DoubleMatrix(int width, int height, ToDoubleFunction<GridPos> generator) {
		data = new double[height][width];
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				data[y][x] = generator.applyAsDouble(new GridPos(x, y));
			}
		}
	}
	
	public DoubleMatrix(double[][] contents) {
		data = contents;
	}
	
	public double get(int x, int y) {
		return data[y][x];
	}
	
	@Override
	public DoubleMatrix add(DoubleMatrix other) {
		verifyEqualSize(other);
		
		int width = getWidth();
		int height = getHeight();
		double[][] sum = new double[width][height];
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				sum[y][x] = data[y][x] + other.data[y][x];
			}
		}
		
		return new DoubleMatrix(sum);
	}
	
	@Override
	public DoubleMatrix sub(DoubleMatrix other) {
		verifyEqualSize(other);
		
		int width = getWidth();
		int height = getHeight();
		double[][] difference = new double[width][height];
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				difference[y][x] = data[y][x] - other.data[y][x];
			}
		}
		
		return new DoubleMatrix(difference);
	}
	
	public boolean isIdentity(double tolerance) {
		if (!isSquareShaped()) {
			return false;
		}
		
		int width = getWidth();
		int height = getHeight();
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				double expected = (x == y) ? 1 : 0;
				
				if (Math.abs(expected - data[y][x]) > tolerance) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean isSquareShaped() {
		return getWidth() == getHeight();
	}
	
	/**
	 * Calculates the determinant of this matrix.
	 * 
	 * @return The determinant
	 * @throws IllegalStateException If the matrix is not square-shaped
	 */
	public double determinant() {
		if (!isSquareShaped()) {
			throw new IllegalStateException("Can't calculate the determinant of a non-square matrix.");
		}
		
		return determinant(data);
	}
	
	public DoubleMatrix transpose() {
		int width = getWidth();
		int height = getHeight();
		
		double[][] result = new double[width][height];
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				result[x][y] = data[y][x];
			}
		}
		
		return new DoubleMatrix(result);
	}
	
	/**
	 * Calculates the inverse of this matrix.
	 * 
	 * @return The inverse
	 * @throws IllegalStateException If the matrix is not square-shaped or the determinant is zero
	 */
	public DoubleMatrix inverse() {
		if (!isSquareShaped()) {
			throw new IllegalStateException("Can't calculate the inverse of a non-square matrix.");
		} else if (determinant() == 0) {
			throw new IllegalStateException("Can't calculate the inverse matrix when the determinant is zero.");
		}
		
		// Source: https://github.com/rchen8/Algorithms/blob/master/Matrix.java
		
		int width = getWidth();
		int height = getHeight();
		double[][] inverse = new double[height][width];
		
		// minors and cofactors
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				inverse[y][x] = Math.pow(-1, y + x) * determinant(minor(data, y, x));
			}
		}
		
		// adjugate and determinant
		
		double det = 1D / determinant();
		for (int y=0; y<height; y++) {
			for (int x=0; x<=y; x++) {
				double tmp = inverse[y][x];
				inverse[y][x] = inverse[x][y] * det;
				inverse[x][y] = tmp * det;
			}
		}
		
		return new DoubleMatrix(inverse);
	}
	
	private double determinant(double[][] mat) {
		// Source: https://github.com/rchen8/Algorithms/blob/master/Matrix.java

		if (mat.length == 2) {
			return (mat[0][0] * mat[1][1]) - (mat[0][1] * mat[1][0]);
		} else {
			double det = 0;
			
			for (int x=0; x<mat[0].length; x++) {
				det += Math.pow(-1, x) * mat[0][x] * determinant(minor(mat, 0, x));
			}
			
			return det;
		}
	}
	
	private double[][] minor(double[][] mat, int row, int col) {
		// Source: https://github.com/rchen8/Algorithms/blob/master/Matrix.java
		
		int width = mat[0].length;
		int height = mat.length;
		
		double[][] minor = new double[height - 1][width - 1];
		
		for (int y=0; y<height; y++) {
			for (int x=0; y!=row && x<width; x++) {
				if (x != col) {
					int resX = (x < col) ? x : x - 1;
					int resY = (y < row) ? y : y - 1;
					
					minor[resY][resX] = mat[y][x];
				}
			}
		}
		
		return minor;
	}
	
	/**
	 * Creates a minor matrix, i.e. this matrix excluding
	 * a specified row and a specified column. The size
	 * of the result is thus [width - 1, height - 1]
	 * 
	 * @param row - The row to be excluded
	 * @param col - The column to be excluded
	 * @return The minor
	 */
	public DoubleMatrix minor(int row, int col) {
		return new DoubleMatrix(minor(data, row, col));
	}

	public void verifyEqualSize(DoubleMatrix other) {
		if (!getSize().equals(other.getSize())) {
			throw new SizeMismatchException("matrix size", getSize(), "matrix size", other.getSize());
		}
	}
	
	public Vector2D getSize() {
		return new Vector2D(getWidth(), getHeight());
	}
	
	public DoubleMatrix multiply(double scalar) {
		int width = getWidth();
		int height = getHeight();
		double[][] product = new double[width][height];
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				product[y][x] = data[y][x] * scalar;
			}
		}
		
		return new DoubleMatrix(product);
	}
	
	public DoubleMatrix multiply(DoubleVector vector) {
		return multiply(vector.asMatrix());
	}
	
	public DoubleMatrix multiply(Vector2D vector) {
		return multiply(vector.asMatrix());
	}
	
	public DoubleMatrix multiply(Vector3D vector) {
		return multiply(vector.asMatrix());
	}
	
	/**
	 * Multiplies this matrix with another one.<br><br>
	 * 
	 * this * other
	 * 
	 * @param right
	 * @return MatA * MatB
	 */
	@Override
	public DoubleMatrix multiply(DoubleMatrix right) {
		if (getWidth() != right.getHeight()) {
			throw new ArithmeticException("The width of this matrix need to equal the height of the other matrix.");
		}
		
		double[][] product = new double[getHeight()][right.getWidth()];
		
		for (int y=0; y<getHeight(); y++) {
			for (int x=0; x<right.getWidth(); x++) {
				double cell = 0;
				
				for (int i=0; i<data[y].length; i++) {
					cell += data[y][i] * right.data[i][x];
				}
				
				product[y][x] = cell;
			}
		}
		
		return new DoubleMatrix(product);
	}

	public int getWidth() {
		return data[0].length;
	}

	public int getHeight() {
		return data.length;
	}
	
	@Deprecated
	public int getRows() {
		return getHeight();
	}

	@Deprecated
	public int getCols() {
		return getWidth();
	}
	
	public DoubleTensor asTensor() {
		return new DoubleTensor(data);
	}
	
	@Override
	public String toString() {
		String s = "[Matrix]\n";
		
		for (int y=0; y<getHeight(); y++) {
			for (int x=0; x<getWidth(); x++) {
				s += data[y][x] + " ";
			}
			
			s += "\n";
		}
		
		return s;
	}

	@Override
	public Iterator<double[]> iterator() {
		return new ArrayIterator<>(data);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(data);
		return result;
	}

	@Override
	public boolean equals(DoubleMatrix other, double tolerance) {
		if (!getSize().equals(other.getSize())) {
			return false;
		}
		
		int width = getWidth();
		int height = getHeight();
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				if (Math.abs(data[y][x] - other.data[y][x]) > tolerance) {
					return false;
				}
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
		DoubleMatrix other = (DoubleMatrix) obj;
		if (!Arrays.deepEquals(data, other.data)) {
			return false;
		}
		return true;
	}
}
