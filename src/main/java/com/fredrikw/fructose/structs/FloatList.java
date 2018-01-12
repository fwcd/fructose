package com.fredrikw.fructose.structs;

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
		ensureCapacity();
		data[size] = v;
		size++;
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
		return data;
	}
}
