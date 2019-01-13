package fwcd.fructose.math;

import fwcd.fructose.math.algebra.DivisionRingElement;
import fwcd.fructose.operations.ToleranceEquatable;

/**
 * A hypercomplex number (represented using four doubles).
 */
public class Quaternion implements DivisionRingElement<Quaternion>, ToleranceEquatable<Quaternion> {
	public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);
	public static final Quaternion ONE = new Quaternion(1, 0, 0, 0);
	public static final Quaternion I = new Quaternion(0, 1, 0, 0);
	public static final Quaternion J = new Quaternion(0, 0, 1, 0);
	public static final Quaternion K = new Quaternion(0, 0, 0, 1);
	
	private final double real;
	private final double imagI;
	private final double imagJ;
	private final double imagK;
	
	private Quaternion(double real, double imagI, double imagJ, double imagK) {
		this.real = real;
		this.imagI = imagI;
		this.imagJ = imagJ;
		this.imagK = imagK;
	}
	
	public static Quaternion ofReal(double real) {
		return of(real, 0, 0, 0);
	}
	
	public static Quaternion ofComplex(double real, double imagI) {
		return of(real, imagI, 0, 0);
	}
	
	public static Quaternion of(Real real) {
		return ofReal(real.getValue());
	}
	
	public static Quaternion of(Complex complex) {
		return ofComplex(complex.getReal(), complex.getImag());
	}
	
	public static Quaternion of(double real, double imagI, double imagJ, double imagK) {
		return new Quaternion(real, imagI, imagJ, imagK);
	}
	
	public double getReal() { return real; }
	
	public double getImagI() { return imagI; }
	
	public double getImagJ() { return imagJ; }
	
	public double getImagK() { return imagK; }
	
	@Override
	public Quaternion add(Quaternion rhs) {
		return of(real + rhs.real, imagI + rhs.imagI, imagJ + rhs.imagJ, imagK + rhs.imagK);
	}
	
	public Quaternion add(Real rhs) {
		return of(real + rhs.getValue(), imagI, imagJ, imagK);
	}
	
	public Quaternion add(Complex rhs) {
		return of(real + rhs.getReal(), imagI + rhs.getImag(), imagJ, imagK);
	}
	
	@Override
	public Quaternion sub(Quaternion rhs) {
		return of(real - rhs.real, imagI - rhs.imagI, imagJ - rhs.imagJ, imagK - rhs.imagK);
	}
	
	public Quaternion sub(Real rhs) {
		return of(real - rhs.getValue(), imagI, imagJ, imagK);
	}
	
	public Quaternion sub(Complex rhs) {
		return of(real - rhs.getReal(), imagI - rhs.getImag(), imagJ, imagK);
	}
	
	public Quaternion conjugate() {
		return new Quaternion(real, -imagI, -imagJ, -imagK);
	}
	
	@Override
	public Quaternion multiply(Quaternion rhs) {
		return of(
			(real * rhs.real)  - (imagI * rhs.imagI) - (imagJ * rhs.imagJ) - (imagK * rhs.imagK),
			(real * rhs.imagI) + (imagI * rhs.real)  + (imagJ * rhs.imagK) - (imagK * rhs.imagJ),
			(real * rhs.imagJ) - (imagI * rhs.imagK) + (imagJ * rhs.real)  + (imagK * rhs.imagI),
			(real * rhs.imagK) + (imagI * rhs.imagJ) - (imagJ * rhs.imagI) + (imagK * rhs.real)
		);
	}
	
	public Quaternion multiply(Real rhs) {
		double scalar = rhs.getValue();
		return of(real * scalar, imagI * scalar, imagJ * scalar, imagK * scalar);
	}
	
	@Override
	public Quaternion leftDivide(Quaternion rhs) {
		return rhs.reciprocal().multiply(this);
	}
	
	@Override
	public Quaternion rightDivide(Quaternion rhs) {
		return multiply(rhs.reciprocal());
	}
	
	public Quaternion cross(Quaternion rhs) {
		return new Quaternion(0, (imagJ * rhs.imagK) - (imagK * rhs.imagJ), (imagK * rhs.imagI) - (imagI * rhs.imagK), (imagI * rhs.imagJ) - (imagJ * rhs.imagI));
	}
	
	public double dot(Quaternion rhs) {
		return (real * rhs.real) + (imagI + rhs.imagI) + (imagJ + rhs.imagJ) + (imagK + rhs.imagK);
	}
	
	public double abs() {
		return Math.sqrt((real * real) + (imagI * imagI) + (imagJ * imagJ) + (imagK * imagK));
	}
	
	@Override
	public Quaternion reciprocal() {
		double absSq = (real * real) + (imagI * imagI) + (imagJ * imagJ) + (imagK * imagK);
		return of(real / absSq, -imagI / absSq, -imagJ / absSq, -imagK / absSq);
	}
	
	@Override
	public Quaternion negate() {
		return new Quaternion(-real, -imagI, -imagJ, -imagK);
	}
	
	@Override
	public boolean equals(Quaternion rhs, double epsilon) {
		return Math.abs(real - rhs.real) < epsilon
			&& Math.abs(imagI - rhs.imagI) < epsilon
			&& Math.abs(imagJ - rhs.imagJ) < epsilon
			&& Math.abs(imagK - rhs.imagK) < epsilon;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		Quaternion other = (Quaternion) obj;
		return real == other.real
			&& imagI == other.imagI
			&& imagJ == other.imagJ
			&& imagK == other.imagK;
	}
	
	@Override
	public int hashCode() {
		return 27
			* Double.hashCode(real)
			* Double.hashCode(imagI)
			* Double.hashCode(imagJ)
			* Double.hashCode(imagK);
	}
	
	@Override
	public String toString() {
		return "(" + real + " + " + imagI + "i + " + imagJ + "j + " + imagK + "k)";
	}
}
