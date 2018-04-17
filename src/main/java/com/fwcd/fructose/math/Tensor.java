package com.fwcd.fructose.math;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;

import com.fwcd.fructose.exception.SizeMismatchException;
import com.fwcd.fructose.geometry.Matrix;
import com.fwcd.fructose.geometry.Vector;
import com.fwcd.fructose.structs.DoubleList;

/**
 * An n-dimensional, immutable array of doubles
 * and a generalization of vectors and matrices.
 * 
 * @author Fredrik
 *
 */
public class Tensor implements Serializable {
	private static final long serialVersionUID = 2125389524338803099L;
	/**
	 * <p>The components. Data is stored in the following order
	 * (analogeous to multi-dimensional arrays, brackets are only
	 * used for the purpose of visualization):</p>
	 * 
	 * <ul>
	 * <li>0D (scalar): [0]</li>
	 * <li>1D (vector): [5, 2, 6, 1, 8]                      - length: 5</li>
	 * <li>2D (matrix): [[2, 4, 2], [9, 5, 3]]               - width: 3, height: 2</li>
	 * <li>3D         : [[[4, 2], [1, 2]], [[1, 2], [8, 5]]] - width: 2, height: 2, depth: 2</li>
	 * </ul>
	 * 
	 * <p><b>THIS ARRAY SHOULD NEVER BE MUTATED OR EXPOSED!!</b></p>
	 */
	private final double[] data;
	/**
	 * <p>The dimensions of this tensor. They contain sequentially:</p>
	 * 
	 * <p>[width, height, depth, ...]</p>
	 * 
	 * <p><b>THIS ARRAY SHOULD NEVER BE MUTATED OR EXPOSED!!</b></p>
	 */
	private final int[] dimensions;
	
	public Tensor(double... vector) {
		data = vector;
		
		if (vector.length > 1) {
			dimensions = new int[] {vector.length};
		} else {
			dimensions = new int[0];
		}
	}
	
	public Tensor(double[][] matrix) {
		int width = matrix[0].length;
		int height = matrix.length;
		DoubleList buffer = new DoubleList(width * height);
		
		for (double[] row : matrix) {
			buffer.addAll(row);
		}
		
		data = buffer.toArray();
		dimensions = new int[] {width, height};
	}
	
	public Tensor(double[][][] data3d) {
		int width = data3d[0][0].length;
		int height = data3d[0].length;
		int depth = data3d.length;
		DoubleList buffer = new DoubleList(width * height * depth);
		
		for (double[][] slice : data3d) {
			for (double[] row : slice) {
				buffer.addAll(row);
			}
		}
		
		data = buffer.toArray();
		dimensions = new int[] {width, height, depth};
	}
	
	public Tensor(double[] data, int[] dimensions) {
		this.data = data;
		this.dimensions = dimensions;
	}
	
	/**
	 * <p>Creates a new tensor containing the data of
	 * this one using a provided rank. This method
	 * is really useful when dealing with vector
	 * row/column-duality.</p>
	 * 
	 * <p>Example - rank 1 to rank 3:</p>
	 * 
	 * <p>[1, 2, 3] to [[[1, 2, 3]]]</p>
	 * 
	 * @param higherRank - The new rank (must be higher or equal to the current)
	 * @return The resulting tensor
	 */
	public Tensor withRank(int higherRank) {
		if (higherRank < getRank()) {
			throw new IllegalArgumentException("New rank has to be larger or equal to the current rank");
		}
		
		return new Tensor(data, Arrays.copyOf(dimensions, higherRank));
	}
	
	public Tensor add(Tensor other) {
		if (!Arrays.equals(dimensions, other.dimensions)) {
			throw new SizeMismatchException(
					"tensor size",
					Arrays.toString(dimensions),
					"tensor size",
					Arrays.toString(dimensions)
			);
		}
		
		double[] result = new double[data.length];
		
		for (int i=0; i<data.length; i++) {
			result[i] = data[i] + other.data[i];
		}
		
		return new Tensor(result, dimensions);
	}
	
	public Tensor combineElementwise(Tensor other, DoubleBinaryOperator mapper) {
		if (!Arrays.equals(dimensions, other.dimensions)) {
			throw new SizeMismatchException(
					"tensor size",
					Arrays.toString(dimensions),
					"tensor size",
					Arrays.toString(dimensions)
			);
		}
		
		double[] result = new double[data.length];
		
		for (int i=0; i<data.length; i++) {
			result[i] = mapper.applyAsDouble(data[i], other.data[i]);
		}
		
		return new Tensor(result, dimensions);
	}
	
	public boolean isScalar() {
		return data.length == 1;
	}

	public boolean isColVector() {
		return data.length == getHeight();
	}
	
	public boolean isRowVector() {
		return data.length == getWidth();
	}

	public boolean isMatrix() {
		return data.length == (getWidth() * getHeight());
	}
	
	public int getWidth() {
		try {
			return dimensions[0];
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalStateException("Tensor has no width.");
		}
	}
	
	public int getHeight() {
		try {
			return dimensions[1];
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalStateException("Tensor has no height.");
		}
	}
	
	public int getDepth() {
		try {
			return dimensions[2];
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalStateException("Tensor has no depth.");
		}
	}
	
	/**
	 * Creates a new scalar from this tensor.
	 * 
	 * @return The scalar
	 * @throws IllegalStateException If the tensor has more than one component
	 */
	public double asScalar() {
		if (isScalar()) {
			return data[0];
		} else {
			throw new IllegalStateException("Tensor needs to be of rank 1 to conform to a scalar.");
		}
	}
	
	/**
	 * Creates a new {@link Vector} from this tensor.
	 * Note that both row and column vectors will be accepted.
	 * 
	 * @return The vector
	 * @throws IllegalStateException If the tensor is neither a row nor a column vector
	 */
	public Vector asVector() {
		if (isRowVector() || isColVector()) {
			return new Vector(data);
		} else {
			throw new IllegalStateException("Tensor needs to be of rank 1 to conform to a vector.");
		}
	}
	
	/**
	 * Creates a new {@link Matrix} from this tensor.
	 * 
	 * @return The matrix
	 * @throws IllegalStateException If the tensor is not a matrix
	 */
	public Matrix asMatrix() {
		if (isMatrix()) {
			int width = dimensions[0];
			int height = dimensions[1];
			
			double[][] result = new double[height][width];
			
			for (int i=0; i<data.length; i++) {
				int x = i % width;
				int y = i / width;
				
				result[y][x] = data[i];
			}
			
			return new Matrix(result);
		} else {
			throw new IllegalStateException("Tensor needs to be of rank 2 to conform to a matrix.");
		}
	}
	
	/**
	 * <p>Fetches the rank of this tensor.</p>
	 * 
	 * <p>Meaning:</p>
	 * 
	 * <ul>
	 * <li>0 = scalar</li>
	 * <li>1 = vector</li>
	 * <li>2 = matrix</li>
	 * <li>...</li>
	 * </ul>
	 * 
	 * @return The rank of this tensor or the "dimensions"
	 */
	public int getRank() {
		return dimensions.length;
	}
	
	/**
	 * <p>Fetches the dimensions of this tensor. They contain sequentially:</p>
	 * 
	 * <p>[width, height, depth, ...]</p>
	 * 
	 * @return The array containing the dimensions
	 */
	public int[] getDimensions() {
		return Arrays.copyOf(dimensions, dimensions.length);
	}
	
	/**
	 * <p>Fetches the value at the given coordinate vector.
	 * It works through generalization of the following formulas:</p>
	 * 
	 * 1 dimension: offset = x<br>
	 * 2 dimensions: offset = (y * w) + x<br>
	 * 3 dimensions: offset = (z * (w * h)) + (y * w) + x<br>
	 * ...
	 * 
	 * @param coords - The "position" in this tensor
	 * @return The value at that position
	 */
	public double get(int... coords) {
		if (coords.length != dimensions.length) {
			throw new IllegalArgumentException("Tensor coordinates need to have the same amount of values as there are dimensions.");
		}
		
		for (int i=0; i<coords.length; i++) {
			if (coords[i] >= dimensions[i]) {
				throw new IndexOutOfBoundsException("The coordinates are outside of the specified bounds.");
			}
		}
		
		int factor = 1;
		int offset = 0;
		
		for (int i=0; i<coords.length; i++) {
			offset += coords[i] * factor;
			factor *= dimensions[i];
		}
		
		return data[offset];
	}
	
	/**
	 * Internal implementation class to assist with
	 * creating a string representation of the tensor.
	 */
	private class Stringgifier {
		private int offset = 0;
		
		private String get(int depth) {
			int maxDepth = getRank() - 1;
			
			if (depth == maxDepth) {
				return Double.toString(data[offset++]);
			} else if (depth < maxDepth) {
				StringBuilder builder = new StringBuilder("[");
				
				int length = dimensions[depth + 1];
				for (int i=0; i<length; i++) {
					builder.append(get(depth + 1));
					if ((i + 1) < length) {
						builder.append(", ");
					}
				}
				
				return builder.append(']').toString();
			} else {
				throw new IllegalStateException("Depth exceeded max depth.");
			}
		}
	}
	
	@Override
	public String toString() {
		return "[Tensor rank " + Integer.toString(getRank()) + "]" + new Stringgifier().get(-1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + Arrays.hashCode(dimensions);
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
		Tensor other = (Tensor) obj;
		if (!Arrays.equals(data, other.data)) {
			return false;
		}
		if (!Arrays.equals(dimensions, other.dimensions)) {
			return false;
		}
		return true;
	}
}
