package fwcd.fructose.math;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Convenience methods for use with vectors and matrices.
 */
public final class Numbers {
	private Numbers() {}
	
	/** Creates a vector of real numbers. */
	public static Vector<Real> realVector(double... values) {
		return new Vector<>(Arrays.stream(values)
				.mapToObj(Real::of)
				.collect(Collectors.toList()));
	}
	
	/** Creates a matrix of real numbers. */
	public static Matrix<Real> realMatrix(double[][] values) {
		return new Matrix<>(Arrays.stream(values)
				.map(row -> Arrays.stream(row)
					.mapToObj(Real::of)
					.collect(Collectors.toList()))
				.collect(Collectors.toList()));
	}
	
	/** Creates a complex vector of real numebrs. */
	public static Vector<Complex> complexVector(double... values) {
		return new Vector<>(Arrays.stream(values)
				.mapToObj(Complex::ofReal)
				.collect(Collectors.toList()));
	}
	
	/** Creates a complex vector. */
	public static Vector<Complex> complexVector(double[][] values) {
		return new Vector<>(Arrays.stream(values)
				.map(cmplx -> Complex.of(cmplx[0], cmplx[1]))
				.collect(Collectors.toList()));
	}
	
	/** Creates a complex matrix of real numbers. */
	public static Matrix<Complex> complexMatrix(double[][] reals) {
		return new Matrix<>(Arrays.stream(reals)
				.map(row -> Arrays.stream(row)
					.mapToObj(Complex::ofReal)
					.collect(Collectors.toList()))
				.collect(Collectors.toList()));
	}
	
	/** Creates a complex matrix. */
	public static Matrix<Complex> complexMatrix(double[][][] values) {
		return new Matrix<>(Arrays.stream(values)
				.map(row -> Arrays.stream(row)
					.map(cmplx -> Complex.of(cmplx[0], cmplx[1]))
					.collect(Collectors.toList()))
				.collect(Collectors.toList()));
	}
}
