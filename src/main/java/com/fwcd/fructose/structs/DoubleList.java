package com.fwcd.fructose.structs;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import com.fwcd.fructose.Copyable;

public class DoubleList implements Copyable<DoubleList> {
	private double[] data;
	private int size = 0;
	
	public DoubleList() {
		this(10);
	}
	
	public DoubleList(int initialSize) {
		data = new double[initialSize];
	}
	
	public DoubleList(double[] data) {
		this.data = data;
		size = data.length;
	}
	
	public DoubleList(DoubleList other) {
		data = Arrays.copyOf(other.data, other.data.length);
		size = other.size;
	}
	
	private void ensureCapacity() {
		if (size >= data.length - 1) {
			double[] newArr = Arrays.copyOf(data, data.length + 10);
			data = newArr;
		}
	}
	
	public int size() {
		return size;
	}
	
	public DoubleList filter(DoublePredicate predicate) {
		DoubleList result = copy();
		result.filterInPlace(predicate);
		return result;
	}
	
	public void filterInPlace(DoublePredicate predicate) {
		IntList toBeRemoved = new IntList();
		for (int i=0; i<size; i++) {
			if (!predicate.test(data[i])) {
				toBeRemoved.add(i);
			}
		}
		
		remove(toBeRemoved.toArray());
	}
	
	public DoubleList map(DoubleUnaryOperator mapper) {
		DoubleList result = copy();
		result.mapInPlace(mapper);
		return result;
	}
	
	public void mapInPlace(DoubleUnaryOperator mapper) {
		for (int i=0; i<size; i++) {
			data[i] = mapper.applyAsDouble(data[i]);
		}
	}
	
	public double sum() {
		double sum = 0;
		
		for (int i=0; i<size; i++) {
			sum += data[i];
		}
		
		return sum;
	}
	
	public double reduce(DoubleBinaryOperator associativeAccumulator) {
		double result = data[0];
		
		for (int i=1; i<size; i++) {
			result = associativeAccumulator.applyAsDouble(result, data[i]);
		}
		
		return result;
	}
	
	public void add(double v) {
		size++;
		ensureCapacity();
		data[size - 1] = v;
	}
	
	public void addAll(double... v) {
		int offset = size;
		size += v.length;
		ensureCapacity();
		System.arraycopy(v, 0, data, offset, v.length);
	}
	
	public void addAll(DoubleList list) {
		addAll(list.data);
	}
	
	public void removeLast() {
		size--;
	}
	
	public void remove(int... indices) {
		Arrays.sort(indices);
		int shift = 0;
		for (int removingIndex : indices) {
			for (int j=removingIndex+1+shift; j<size; j++) {
				data[j - 1] = data[j];
			}
			size--;
			shift--;
		}
	}
	
	public double get(int i) {
		if (i < size) {
			return data[i];
		} else {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
	}
	
	public double[] toArray() {
		return Arrays.copyOf(data, size);
	}
	
	public void forEach(DoubleConsumer consumer) {
		for (int i=0; i<size; i++) {
			consumer.accept(data[i]);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("[");
		
		for (int i=0; i<size; i++) {
			s.append(data[i]).append(", ");
		}
		
		return s.delete(s.length() - 2, s.length()).append(']').toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (int i=0; i<size; i++) {
			result = (int) (prime * result + data[i]);
		}
		result = prime * result + size;
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
		DoubleList other = (DoubleList) obj;
		if (size != other.size) {
			return false;
		}
		for (int i=0; i<size; i++) {
			if (data[i] != other.data[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public DoubleList copy() {
		return new DoubleList(this);
	}
}
