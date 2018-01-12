package com.fredrikw.fructose.math;

import java.util.Objects;

/**
 * A complex number.
 * 
 * @author Fredrik
 *
 */
public class Complex {
	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex I = new Complex(0, 1);
	
	private final double real;
	private final double imag;
	
	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	public double getReal() {
		return real;
	}
	
	public double getImag() {
		return imag;
	}
	
	public Complex add(Complex other) {
		return new Complex(real + other.real, imag + other.imag);
	}
	
	public Complex sub(Complex other) {
		return new Complex(real - other.real, imag - other.imag);
	}
	
	public Complex invertReal() {
		return new Complex(-real, imag);
	}
	
	public Complex invertImag() {
		return new Complex(real, -imag);
	}
	
	public Complex conjugate() {
		return invertImag();
	}
	
	public Complex multiply(double factor) {
		return new Complex(factor * real, factor * imag);
	}
	
	public Complex divide(double denom) {
		return new Complex(real / denom, imag / denom);
	}
	
	public Complex exp() {
		return new Complex(Math.exp(real) * Math.cos(imag), Math.exp(real) * Math.sin(imag));
	}
	
	public Complex sin() {
		return new Complex(Math.sin(real) * Math.cosh(imag), Math.cos(real) * Math.sinh(imag));
	}
	
	public Complex cos() {
		return new Complex(Math.cos(real) * Math.cosh(imag), -Math.sin(real) * Math.sinh(imag));
	}
	
	public Complex tan() {
		return sin().divide(cos());
	}
	
	public Complex signum() {
		return divide(abs());
	}
	
	public Complex reciprocal() {
		double scale = (real * real) + (imag * imag);
		return new Complex(real / scale, -imag / scale);
	}
	
	public Complex divide(Complex other) {
		return multiply(other.reciprocal());
	}
	
	public double getTheta() {
		return Math.atan2(imag, real);
	}
	
	public Complex multiply(Complex other) {
		// Firsts + outers + inners + lasts:
		// (a+bi)(c+di) = ac + adi + bci + bdi^2
		
		// i^2 = -1, so the equation becomes:
		// (a+bi)(c+di) = ac + adi + bci - bd
		//              = (ac - bd) + (ad + bc)i
		
		return new Complex(
				(real * other.real) - (imag * other.imag),
				(real * other.imag) + (imag * other.real)
		);
	}
	
	public Complex square() {
		return multiply(this);
	}
	
	public double abs() {
		return Math.hypot(real, imag);
	}
	
	public double absSquared() {
		double abs = abs();
		return abs * abs;
	}
	
	public Complex pow(int x) {
		if (x < 1) {
			throw new IllegalArgumentException("Can't exponentiate a complex number with an exponent below 1 (for now)");
		}
		
		Complex result = this;
		
		for (int i=0; i<x-1; i++) {
			result = result.multiply(this);
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		if (imag > 0) {
			return "(" + Double.toString(real) + " + " + Double.toString(imag) + "i)";
		} else if (imag < 0) {
			return "(" + Double.toString(real) + " - " + Double.toString(-imag) + "i)";
		} else {
			return Double.toString(real);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(real, imag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		
		Complex other = (Complex) obj;
		return real == other.real && imag == other.imag;
	}
}
