package fwcd.fructose.math;

/**
 * A real number (represented as a double).
 */
public class Real extends Number implements Numeric<Real> {
	private static final long serialVersionUID = 621483791896382089L;
	private double value;
	
	private Real(double value) {
		this.value = value;
	}
	
	public static Real of(double value) { return new Real(value); }
	
	public Complex toComplex() { return Complex.ofReal(value); }
	
	public Real pow(Real rhs) { return of(Math.pow(value, rhs.value)); }
	
	public Real exp() { return of(Math.exp(value)); }
	
	public Real sin() { return of(Math.sin(value)); }
	
	public Real cos() { return of(Math.cos(value)); }
	
	@Override
	public Real add(Real rhs) { return of(value + rhs.value); }

	@Override
	public Real sub(Real rhs) { return of(value - rhs.value); }
	
	@Override
	public Real divide(Real rhs) { return of(value / rhs.value); }

	@Override
	public Real multiply(Real rhs) { return of(value * rhs.value); }
	
	@Override
	public Real reciprocal() { return of(1D / value); }
	
	@Override
	public Real negate() { return of(-value); }
	
	public double value() { return value; }

	@Override
	public int intValue() { return (int) value; }

	@Override
	public long longValue() { return (long) value; }

	@Override
	public float floatValue() { return (float) value; }

	@Override
	public double doubleValue() { return value; }
	
	@Override
	public String toString() { return Double.toString(value); }
	
	@Override
	public boolean equals(Object obj) { return value == ((Real) obj).value; }
	
	@Override
	public boolean equals(Real rhs, double tolerance) {
		return Math.abs(value - rhs.value) <= tolerance;
	}
	
	@Override
	public int hashCode() { return Double.hashCode(value); }
}
