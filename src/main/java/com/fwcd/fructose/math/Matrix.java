package com.fwcd.fructose.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.fwcd.fructose.ListUtils;
import com.fwcd.fructose.operations.Addable;
import com.fwcd.fructose.operations.Multipliable;
import com.fwcd.fructose.operations.Subtractable;
import com.fwcd.fructose.operations.ToleranceEquatable;

/**
 * An immutable, numeric matrix.
 */
public class Matrix<V extends Numeric<V>> implements
		Addable<Matrix<V>, Matrix<V>>,
		Subtractable<Matrix<V>, Matrix<V>>,
		Multipliable<Matrix<V>, Matrix<V>>,
		ToleranceEquatable<Matrix<V>> {
	private final List<List<V>> data;
	
	public Matrix(V[][] data) {
		this.data = Arrays.stream(data)
			.map(it -> new ArrayList<V>())
			.collect(Collectors.toList());
	}
	
	public Matrix(List<List<V>> data) {
		this.data = data;
	}
	
	public V get(int x, int y) {
		return data.get(y).get(x);
	}
	
	public int width() {
		return data.get(0).size();
	}
	
	public int height() {
		return data.size();
	}

	@Override
	public Matrix<V> add(Matrix<V> rhs) {
		return combineElementwise(rhs, (a, b) -> a.add(b));
	}

	@Override
	public Matrix<V> sub(Matrix<V> rhs) {
		return combineElementwise(rhs, (a, b) -> a.sub(b));
	}
	
	public Matrix<V> combineElementwise(Matrix<V> rhs, BinaryOperator<V> combiner) {
		int width = width();
		int height = height();
		List<List<V>> result = new ArrayList<>();
		
		for (int y=0; y<height; y++) {
			List<V> row = new ArrayList<>();
			for (int x=0; x<width; x++) {
				row.add(get(x, y).sub(rhs.get(x, y)));
			}
			result.add(row);
		}
		
		return new Matrix<>(result);
	}
	
	public Matrix<V> map(UnaryOperator<V> mapper) {
		int width = width();
		int height = height();
		List<List<V>> result = new ArrayList<>();
		
		for (int y=0; y<height; y++) {
			List<V> row = new ArrayList<>();
			for (int x=0; x<width; x++) {
				row.add(mapper.apply(get(x, y)));
			}
			result.add(row);
		}
		
		return new Matrix<>(result);
	}

	@Override
	public Matrix<V> multiply(Matrix<V> rhs) {
		if (width() != rhs.height()) {
			throw new ArithmeticException("The width of this matrix need to equal the height of the other matrix.");
		}
		
		List<List<V>> product = new ArrayList<>();
		
		for (int y=0; y<height(); y++) {
			List<V> row = new ArrayList<>();
			for (int x=0; x<rhs.width(); x++) {
				V cell = get(0, y).multiply(rhs.get(x, 0));
				
				for (int i=1; i<data.get(y).size(); i++) {
					cell = cell.add(get(i, y).multiply(rhs.get(x, i)));
				}
				
				row.add(cell);
			}
			product.add(row);
		}
		
		return new Matrix<>(product);
	}
	
	/**
	 * The Tensor product (also called Kronecker product) of this
	 * matrix with another matrix.
	 */
	public Matrix<V> tensorProduct(Matrix<V> other) {
		int width = width();
		int height = height();
		List<List<V>> result = ListUtils.make2DList(height * other.height(), width * other.width(), (x, y) -> null);
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				for (int otherY=0; otherY<other.height(); otherY++) {
					for (int otherX=0; otherX<other.width(); otherX++) {
						int resY = (y * other.height()) + otherY;
						int resX = (x * other.width()) + otherX;
						V value = get(x, y).multiply(other.get(otherX, otherY));
						
						result.get(resY).set(resX, value);
					}
				}
			}
		}
		
		return new Matrix<>(result);
	}
	
	public Matrix<V> multiply(V scalar) {
		return map(v -> v.multiply(scalar));
	}
	
	public Vector<V> multiply(Vector<V> other) {
		int width = width();
		if (width != other.size()) {
			throw new IllegalArgumentException("Vector size needs to equal matrix width!");
		}
		
		List<V> result = new ArrayList<>();
		
		for (int y=0; y<height(); y++) {
			V dot = get(0, y).multiply(other.get(0));
			
			for (int i=1; i<width; i++) {
				dot = dot.add(get(i, y).multiply(other.get(i)));
			}
			
			result.add(dot);
		}
		
		return new Vector<>(result);
	}
	
	public boolean isSquareShaped() {
		return width() == height();
	}
	
	/**
	 * Calculates the determinant of this matrix.
	 * 
	 * @return The determinant
	 * @throws IllegalStateException If the matrix is not square-shaped
	 */
	public V determinant() {
		if (!isSquareShaped()) {
			throw new IllegalStateException("Can't calculate the determinant of a non-square matrix.");
		}
		
		return determinant(data);
	}
	
	public Matrix<V> transpose() {
		int width = width();
		int height = height();
		
		List<List<V>> result = ListUtils.make2DList(width, height, (x, y) -> null);
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				result.get(x).set(y, get(x, y));
			}
		}
		
		return new Matrix<>(result);
	}
	
	/**
	 * Calculates the inverse of this matrix.
	 * 
	 * @return The inverse
	 * @throws IllegalStateException If the matrix is not square-shaped or the determinant is zero
	 */
	public Matrix<V> inverse() {
		if (!isSquareShaped()) {
			throw new IllegalStateException("Can't calculate the inverse of a non-square matrix.");
		}
		
		// Source: https://github.com/rchen8/Algorithms/blob/master/Matrix.java
		
		int width = width();
		int height = height();
		List<List<V>> inverse = ListUtils.make2DList(height, width, (x, y) -> null);
		
		// minors and cofactors
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				inverse.get(y).set(x, determinant(minor(data, y, x)).multiply(Real.of(Math.pow(-1, y + x))));
			}
		}
		
		// adjugate and determinant
		
		V det = determinant().reciprocal();
		for (int y=0; y<height; y++) {
			for (int x=0; x<=y; x++) {
				V tmp = inverse.get(y).get(x);
				inverse.get(y).set(x, inverse.get(x).get(y).multiply(det));
				inverse.get(x).set(y, tmp.multiply(det));
			}
		}
		
		return new Matrix<>(inverse);
	}

	@Override
	public boolean equals(Matrix<V> other, double tolerance) {
		if (width() != other.width() || height() != other.height()) {
			return false;
		}
		
		int width = width();
		int height = height();
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				if (!get(x, y).equals(other.get(x, y), tolerance)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private V determinant(List<List<V>> mat) {
		// Source: https://github.com/rchen8/Algorithms/blob/master/Matrix.java

		if (mat.size() == 2) {
			V first = mat.get(0).get(0).multiply(mat.get(1).get(1));
			V second = mat.get(0).get(1).multiply(mat.get(1).get(0));
			return first.sub(second);
		} else {
			V det = mat.get(0).get(0)
					.multiply(determinant(minor(mat, 0, 0)));
			
			for (int x=1; x<mat.get(0).size(); x++) {
				V delta = mat.get(0).get(x)
						.multiply(Real.of(Math.pow(-1, x)))
						.multiply(determinant(minor(mat, 0, x)));
				det = det.add(delta);
			}
			
			return det;
		}
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
	public Matrix<V> minor(int row, int col) {
		return new Matrix<>(minor(data, row, col));
	}
	
	private List<List<V>> minor(List<List<V>> mat, int row, int col) {
		// Source: https://github.com/rchen8/Algorithms/blob/master/Matrix.java
		
		int width = mat.get(0).size();
		int height = mat.size();
		
		List<List<V>> minor = ListUtils.make2DList(height - 1, width - 1, (x, y) -> null);
		
		for (int y=0; y<height; y++) {
			for (int x=0; y!=row && x<width; x++) {
				if (x != col) {
					int resX = (x < col) ? x : x - 1;
					int resY = (y < row) ? y : y - 1;
					
					minor.get(resY).set(resX, mat.get(y).get(x));
				}
			}
		}
		
		return minor;
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}
