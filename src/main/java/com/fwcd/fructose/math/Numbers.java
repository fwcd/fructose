package com.fwcd.fructose.math;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Convenience methods for use with vectors and matrices.
 */
public final class Numbers {
	private Numbers() {}
	
	public static Vector<Real> realVector(double... values) {
		return new Vector<>(Arrays.stream(values)
				.mapToObj(Real::of)
				.collect(Collectors.toList()));
	}
	
	public static Matrix<Real> realMatrix(double[][] values) {
		return new Matrix<>(Arrays.stream(values)
				.map(row -> Arrays.stream(row)
					.mapToObj(Real::of)
					.collect(Collectors.toList()))
				.collect(Collectors.toList()));
	}
	
	public static Vector<Complex> complexVector(double[][] values) {
		return new Vector<>(Arrays.stream(values)
				.map(cmplx -> Complex.of(cmplx[0], cmplx[1]))
				.collect(Collectors.toList()));
	}
	
	public static Matrix<Complex> complexMatrix(double[][][] values) {
		return new Matrix<>(Arrays.stream(values)
				.map(row -> Arrays.stream(row)
					.map(cmplx -> Complex.of(cmplx[0], cmplx[1]))
					.collect(Collectors.toList()))
				.collect(Collectors.toList()));
	}
}
