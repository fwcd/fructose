package fwcd.fructose.math;

import java.util.Objects;

/**
 * A complex number (represented using two doubles).
 */
public class Complex implements Numeric<Complex> {
	public static final Complex ZERO = of(0, 0);
	public static final Complex ONE = of(1, 0);
	public static final Complex I = of(0, 1);
	
	private final double real;
	private final double imag;
	
	private Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	public static Complex ofReal(double real) {
		return of(real, 0);
	}
	
	public static Complex of(double real, double imag) {
		return new Complex(real, imag);
	}
	
	public double getReal() {
		return real;
	}
	
	public double getImag() {
		return imag;
	}
	
	@Override
	public Complex add(Complex rhs) {
		return of(real + rhs.real, imag + rhs.imag);
	}
	
	@Override
	public Complex sub(Complex rhs) {
		return of(real - rhs.real, imag - rhs.imag);
	}
	
	public Complex invertReal() {
		return of(-real, imag);
	}
	
	public Complex invertImag() {
		return of(real, -imag);
	}
	
	public Complex conjugate() {
		return invertImag();
	}
	
	public Complex multiply(double factor) {
		return of(factor * real, factor * imag);
	}
	
	public Complex divide(double denom) {
		return of(real / denom, imag / denom);
	}
	
	public Complex exp() {
		return of(Math.exp(real) * Math.cos(imag), Math.exp(real) * Math.sin(imag));
	}
	
	public Complex sin() {
		return of(Math.sin(real) * Math.cosh(imag), Math.cos(real) * Math.sinh(imag));
	}
	
	public Complex cos() {
		return of(Math.cos(real) * Math.cosh(imag), -Math.sin(real) * Math.sinh(imag));
	}
	
	public Complex tan() {
		return sin().divide(cos());
	}
	
	public Complex signum() {
		return divide(abs());
	}
	
	@Override
	public Complex reciprocal() {
		double scale = (real * real) + (imag * imag);
		return of(real / scale, -imag / scale);
	}
	
	@Override
	public Complex negate() {
		return of(-real, -imag);
	}
	
	public Complex divide(Complex other) {
		return multiply(other.reciprocal());
	}
	
	/**
	 * @return The angle theta between the real axis and this number
	 */
	public double argument() {
		return Math.atan2(imag, real);
	}
	
	@Override
	public Complex multiply(Complex other) {
		// Firsts + outers + inners + lasts:
		// (a+bi)(c+di) = ac + adi + bci + bdi^2
		
		// i^2 = -1, so the equation becomes:
		// (a+bi)(c+di) = ac + adi + bci - bd
		//              = (ac - bd) + (ad + bc)i
		
		return of(
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
	
	public Complex pow(int exponent) {
		if (exponent < 1) {
			return pow((double) exponent);
		}
		
		Complex result = this;
		
		for (int i=0; i<exponent-1; i++) {
			result = result.multiply(this);
		}
		
		return result;
	}
	
	public Complex pow(double exponent) {
		return pow(Complex.ofReal(exponent));
	}
	
	public Complex pow(Complex exponent) {
		double a = real;
		double b = imag;
		double c = exponent.real;
		double d = exponent.imag;
		double aSqPlusBSq = (a * a) + (b * b);
		double baseArg = argument();
		double theta = (c * baseArg) + (0.5 * d * Math.log(aSqPlusBSq));
		
		return of(Math.cos(theta), Math.sin(theta))
				.multiply(Math.pow(aSqPlusBSq, c / 2) * Math.exp(-d * baseArg));
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
	public boolean equals(Complex other, double tolerance) {
		return (Math.abs(real - other.real) < tolerance)
				&& (Math.abs(imag - other.imag) < tolerance);
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

	@Override
	public Complex add(Real rhs) {
		return of(real + rhs.value(), imag);
	}

	@Override
	public Complex sub(Real rhs) {
		return of(real - rhs.value(), imag);
	}

	@Override
	public Complex multiply(Real rhs) {
		return multiply(rhs.value());
	}

	@Override
	public Complex divide(Real rhs) {
		return divide(rhs.value());
	}
}
