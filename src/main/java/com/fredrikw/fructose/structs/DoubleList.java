package com.fredrikw.fructose.structs;

import java.util.Arrays;

public class DoubleList {
	private double[] data;
	private int size = 0;
	
	public DoubleList() {
		this(10);
	}
	
	public DoubleList(int initialSize) {
		data = new double[initialSize];
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
}
