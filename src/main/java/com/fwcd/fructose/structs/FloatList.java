package com.fwcd.fructose.structs;

import java.util.Arrays;

public class FloatList {
	private float[] data;
	private int size = 0;
	
	public FloatList() {
		this(10);
	}
	
	public FloatList(int initialCapacity) {
		data = new float[initialCapacity];
	}
	
	public FloatList(float[] data) {
		this.data = data;
		size = data.length;
	}
	
	private void ensureCapacity() {
		if (size >= data.length - 1) {
			float[] newArr = Arrays.copyOf(data, data.length + 10);
			data = newArr;
		}
	}
	
	public int size() {
		return size;
	}
	
	public void add(float v) {
		size++;
		ensureCapacity();
		data[size - 1] = v;
	}
	
	public void addAll(float... v) {
		int offset = size;
		size += v.length;
		ensureCapacity();
		System.arraycopy(v, 0, data, offset, v.length);
	}
	
	public void addAll(FloatList list) {
		addAll(list.data);
	}
	
	public void removeLast() {
		size--;
	}
	
	public float get(int i) {
		if (i < size) {
			return data[i];
		} else {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
	}
	
	public float[] getMutableUntrimmedArray() {
		return data;
	}
	
	public float[] toArray() {
		return Arrays.copyOf(data, size);
	}

	public float sum() {
		float sum = 0;
		
		for (int i=0; i<size; i++) {
			sum += data[i];
		}
		
		return sum;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}

	@Override
	public boolean equals(Object obj) {
		return Arrays.equals(data, ((FloatList) obj).data);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("[");
		
		for (int i=0; i<size; i++) {
			s.append(data[i]).append(", ");
		}
		
		return s.delete(s.length() - 2, s.length()).append(']').toString();
	}
}
