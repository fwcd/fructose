package com.fwcd.fructose.structs;

import java.util.Arrays;

public class FloatList {
	private float[] data;
	private int size = 0;
	
	public FloatList() {
		this(10);
	}
	
	public FloatList(int initialSize) {
		data = new float[initialSize];
	}
	
	public FloatList(float[] data) {
		this.data = data;
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
}
