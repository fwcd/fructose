package com.fwcd.fructose.math;

import java.util.Arrays;

/**
 * An immutable vector of complex numbers.
 * 
 * @author Fredrik
 *
 */
public class ComplexVector {
	private final Complex[] data;
	
	public ComplexVector(float... data) {
		int size = data.length;
		this.data = new Complex[size];
		
		for (int i=0; i<size; i++) {
			this.data[i] = new Complex(data[i], 0);
		}
	}
	
	public ComplexVector(Complex... data) {
		this.data = data;
	}
	
	public ComplexVector multiply(float scalar) {
		return multiply(new Complex(scalar, 0));
	}
	
	public ComplexVector multiply(Complex scalar) {
		Complex[] result = new Complex[size()];
		
		for (int y=0; y<size(); y++) {
			result[y] = data[y].multiply(scalar);
		}
		
		return new ComplexVector(result);
	}
	
	public Complex get(int i) {
		return data[i];
	}
	
	public int size() {
		return data.length;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(data);
	}
}
