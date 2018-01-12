package com.fredrikw.fructose.math;

import java.util.Arrays;

public class ComplexMatrix {
	private final Complex[][] data;
	
	public ComplexMatrix(float[][] data) {
		int width = data[0].length;
		int height = data.length;
		this.data = new Complex[height][width];
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				this.data[y][x] = new Complex(data[y][x], 0);
			}
		}
	}
	
	public ComplexMatrix(Complex[][] data) {
		this.data = data;
	}
	
	public Complex get(int x, int y) {
		return data[y][x];
	}
	
	public int width() {
		return data[0].length;
	}
	
	public int height() {
		return data.length;
	}
	
	public ComplexMatrix kroneckerProduct(ComplexMatrix other) {
		Complex[][] result = new Complex[height() * other.height()][width() * other.width()];
		
		for (int y=0; y<height(); y++) {
			for (int x=0; x<width(); x++) {
				for (int otherY=0; otherY<other.height(); otherY++) {
					for (int otherX=0; otherX<other.width(); otherX++) {
						int resY = (y * other.height()) + otherY;
						int resX = (x * other.width()) + otherX;
						
						result[resY][resX] = get(x, y).multiply(other.get(otherX, otherY));
					}
				}
			}
		}
		
		return new ComplexMatrix(result);
	}
	
	public ComplexMatrix multiply(float scalar) {
		return multiply(new Complex(scalar, 0));
	}
	
	public ComplexMatrix multiply(Complex scalar) {
		Complex[][] result = new Complex[width()][height()];
		
		for (int y=0; y<height(); y++) {
			for (int x=0; x<width(); x++) {
				result[y][x] = data[y][x].multiply(scalar);
			}
		}
		
		return new ComplexMatrix(result);
	}
	
	public ComplexVector multiply(ComplexVector other) {
		if (width() != other.size()) {
			throw new IllegalArgumentException("Vector size needs to equal matrix width!");
		}
		
		Complex[] result = new Complex[height()];
		
		for (int y=0; y<result.length; y++) {
			Complex dot = Complex.ZERO;
			
			for (int i=0; i<width(); i++) {
				dot = dot.add(get(i, y).multiply(other.get(i)));
			}
			
			result[y] = dot;
		}
		
		return new ComplexVector(result);
	}
	
	public ComplexMatrix multiply(ComplexMatrix other) {
		if (width() != other.height()) {
			throw new IllegalArgumentException("Can't multiply two matrices when A's width doesn't match B's height");
		}
		
		Complex[][] result = new Complex[height()][other.width()];
		
		for (int y=0; y<result.length; y++) {
			for (int x=0; x<result[0].length; x++) {
				Complex dot = Complex.ZERO;
				
				for (int i=0; i<width(); i++) {
					dot = dot.add(get(i, y).multiply(other.get(x, i)));
				}
				
				result[y][x] = dot;
			}
		}
		
		return new ComplexMatrix(result);
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for (Complex[] row : data) {
			s += Arrays.toString(row) + "\n";
		}
		
		return s;
	}
}
