package com.fredrikw.fructose.ml.math;

import java.util.Arrays;

import com.fredrikw.fructose.structs.FloatList;

/**
 * An n-dimensional, mutable array of floats
 * and a generalization of vectors and matrices.
 * 
 * @author Fredrik
 *
 */
public class NNTensor {
	private final float[] data;
	private final int[] dimensions;
	
	public NNTensor(float... vector) {
		data = vector;
		dimensions = new int[] {vector.length};
	}
	
	public NNTensor(float[][] matrix) {
		int width = matrix[0].length;
		int height = matrix.length;
		FloatList buffer = new FloatList(width * height);
		
		for (float[] row : matrix) {
			buffer.addAll(row);
		}
		
		data = buffer.toArray();
		dimensions = new int[] {width, height};
	}
	
	public NNTensor(float[][][] data3d) {
		int width = data3d[0][0].length;
		int height = data3d[0].length;
		int depth = data3d.length;
		FloatList buffer = new FloatList(width * height * depth);
		
		for (float[][] slice : data3d) {
			for (float[] row : slice) {
				buffer.addAll(row);
			}
		}
		
		data = buffer.toArray();
		dimensions = new int[] {width, height, depth};
	}
	
	public NNTensor(float[] data, int[] dimensions) {
		this.data = data;
		this.dimensions = dimensions;
	}
	
	public int getRank() {
		return dimensions.length;
	}
	
	public int[] getDimensions() {
		return dimensions;
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
	public float get(float... coords) {
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
	
	private class Stringgifier {
		private int offset = 0;
		
		private String get(int depth) {
			int maxDepth = getRank() - 1;
			
			if (depth == maxDepth) {
				return Float.toString(data[offset++]);
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
		return "[NNTensor rank " + Integer.toString(getRank()) + "]" + new Stringgifier().get(-1);
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
		NNTensor other = (NNTensor) obj;
		if (!Arrays.equals(data, other.data)) {
			return false;
		}
		if (!Arrays.equals(dimensions, other.dimensions)) {
			return false;
		}
		return true;
	}
}
