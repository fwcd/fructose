package fwcd.fructose.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

import fwcd.fructose.operations.Addable;
import fwcd.fructose.operations.Divisible;
import fwcd.fructose.operations.Multipliable;
import fwcd.fructose.operations.Subtractable;
import fwcd.fructose.operations.ToleranceEquatable;
import fwcd.fructose.util.ListUtils;

/**
 * An immutable, numeric vector.
 */
public class Vector<V extends Numeric<V>> implements
			Addable<Vector<V>, Vector<V>>,
			Subtractable<Vector<V>, Vector<V>>,
			Multipliable<V, Vector<V>>,
			Divisible<V, Vector<V>>,
			ToleranceEquatable<Vector<V>>,
			Iterable<V> {
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
		return zip(rhs, V::add);
	}
	
	@Override
	public Vector<V> sub(Vector<V> rhs) {
		return zip(rhs, V::sub);
	}
	
	@Override
	public Vector<V> multiply(V scalar) {
		return map(v -> v.multiply(scalar));
	}
	
	@Override
	public Vector<V> divide(V scalar) {
		return map(v -> v.divide(scalar));
	}
	
	/** The dot product (inner product) with another vector. */
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
	
	public Vector<V> negate() {
		return map(V::negate);
	}
	
	/** The outer product with another vector. */
	public Matrix<V> outer(Vector<V> rhs) {
		return asColumnMatrix().multiply(rhs.asRowMatrix());
	}
	
	/**
	 * The Kronecker product with another vector.
	 * Both vectors are interpreted as column vectors.
	 */
	public Vector<V> kronecker(Vector<V> rhs) {
		int lSize = size();
		int rSize = rhs.size();
		List<V> result = ListUtils.makeList(lSize * rSize, i -> null);
		for (int i=0; i<lSize; i++) {
			for (int j=0; j<rSize; j++) {
				result.set((i * rSize) + j, get(i).multiply(rhs.get(j)));
			}
		}
		return new Vector<>(result);
	}
	
	/**
	 * @return A matrix representing this vector as a column vector
	 */
	public Matrix<V> asColumnMatrix() {
		final int size = size();
		List<List<V>> result = new ArrayList<>(size);
		
		for (int i=0; i<size; i++) {
			result.add(Collections.singletonList(get(i)));
		}
		
		return new Matrix<>(result);
	}
	
	/**
	 * @return A matrix representing this vector as a row vector
	 */
	public Matrix<V> asRowMatrix() {
		return new Matrix<>(Collections.singletonList(data));
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
	
	@Override
	public Iterator<V> iterator() {
		return data.iterator();
	}
	
	public Vector<V> withAt(int index, V value) {
		List<V> result = new ArrayList<>(data);
		result.set(index, value);
		return new Vector<>(result);
	}
	
	@SafeVarargs
	public final Vector<V> appendedBy(V... values) {
		List<V> result = new ArrayList<>(data.size() + values.length);
		result.addAll(data);
		for (V value : values) {
			result.add(value);
		}
		return new Vector<>(result);
	}
	
	public <R extends Numeric<R>> Vector<R> zip(Vector<V> rhs, BiFunction<V, V, R> zipper) {
		int size = size();
		if (rhs.size() != size) {
			throw new IllegalArgumentException(
				"Tried to zip a vector of size "
				+ size
				+ " with a vector of size "
				+ rhs.size()
			);
		}
		List<R> result = new ArrayList<>();
		for (int i=0; i<size; i++) {
			result.add(zipper.apply(get(i), rhs.get(i)));
		}
		return new Vector<>(result);
	}
	
	public <R extends Numeric<R>> Vector<R> map(Function<V, R> mapper) {
		int size = size();
		List<R> result = new ArrayList<>();
		for (int i=0; i<size; i++) {
			result.add(mapper.apply(get(i)));
		}
		return new Vector<>(result);
	}
	
	public V reduce(BinaryOperator<V> accumulator) {
		int size = size();
		V result = get(0);
		for (int i=1; i<size; i++) {
			result = accumulator.apply(result, get(i));
		}
		return result;
	}
	
	public Stream<V> stream() {
		return data.stream();
	}
}
