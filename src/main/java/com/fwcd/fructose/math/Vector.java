package com.fwcd.fructose.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fwcd.fructose.operations.Addable;
import com.fwcd.fructose.operations.Multipliable;
import com.fwcd.fructose.operations.Subtractable;
import com.fwcd.fructose.operations.ToleranceEquatable;

/**
 * An immutable, numeric vector.
 */
public class Vector<V extends Numeric<V>> implements
			Addable<Vector<V>, Vector<V>>,
			Subtractable<Vector<V>, Vector<V>>,
			Multipliable<V, Vector<V>>,
			ToleranceEquatable<Vector<V>> {
	private final List<V> data;
	
	@SafeVarargs
	public Vector(V... data) {
		this.data = Arrays.asList(data);
	}
	
	public Vector(List<V> data) {
		this.data = data;
	}
	
	@Override
	public Vector<V> add(Vector<V> rhs) {
		int size = size();
		List<V> result = new ArrayList<>(size);
		
		for (int i=0; i<size; i++) {
			result.add(data.get(i).add(rhs.data.get(i)));
		}
		
		return new Vector<>(result);
	}
	
	@Override
	public Vector<V> sub(Vector<V> rhs) {
		int size = size();
		List<V> result = new ArrayList<>(size);

		for (int i=0; i<size; i++) {
			result.add(data.get(i).sub(rhs.data.get(i)));
		}
		
		return new Vector<>(result);
	}
	
	@Override
	public Vector<V> multiply(V scalar) {
		int size = size();
		List<V> result = new ArrayList<>(size);
		
		for (int i=0; i<size; i++) {
			result.add(data.get(i).multiply(scalar));
		}
		
		return new Vector<>(result);
	}
	
	public V dot(Vector<V> rhs) {
		V result = data.get(0).multiply(rhs.data.get(0));
		
		for (int i=1; i<data.size(); i++) {
			result = result.add(data.get(i).multiply(rhs.data.get(i)));
		}
		
		return result;
	}
	
	public V get(int i) {
		return data.get(i);
	}
	
	public int size() {
		return data.size();
	}
	
	@Override
	public boolean equals(Vector<V> other, double tolerance) {
		if (size() != other.size()) {
			return false;
		}
		
		int size = size();
		
		for (int i=0; i<size; i++) {
			if (!get(i).equals(other.get(i), tolerance)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * @return A matrix representing this vector as a column vector
	 */
	public Matrix<V> asMatrix() {
		final int size = size();
		List<List<V>> result = new ArrayList<>(size);
		
		for (int i=0; i<size; i++) {
			result.add(Collections.singletonList(get(i)));
		}
		
		return new Matrix<>(result);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}
