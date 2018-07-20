package com.fwcd.fructose.geometry;

/**
 * A simple, mutable 2D vector implementation.
 * 
 * @author Fredrik
 *
 */
public class MutableVector2D implements Cloneable {
	private double x;
	private double y;
	
	public MutableVector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public MutableVector2D(float length, float angleInRad) {
		x = Math.cos(angleInRad) * length;
		y = Math.sin(angleInRad) * length;
	}
	
	public MutableVector2D(DoubleMatrix matrix) {
		setFrom(matrix);
	}

	public void setFrom(DoubleMatrix matrix) {
		if (matrix.getHeight() != 2 || matrix.getWidth() != 1) {
			throw new RuntimeException("Matrix needs to be 1x2 to be converted to a vector.");
		}
		
		x = matrix.get(0, 0);
		y = matrix.get(0, 1);
	}
	
	/**
	 * Returns this difference between another vector and this.
	 * Convenience method.<br><br>
	 * 
	 * this -> otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public MutableVector2D pointerTo(MutableVector2D other) {
		return other.clone().sub(this);
	}
	
	/**
	 * Adds another vector to this.<br><br>
	 * 
	 * this += otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public MutableVector2D add(MutableVector2D other) {
		x += other.x;
		y += other.y;
		
		return this;
	}
	
	/**
	 * Subtracts another vector from this.<br><br>
	 * 
	 * this -= otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public MutableVector2D sub(MutableVector2D other) {
		x -= other.x;
		y -= other.y;
		
		return this;
	}
	
	/**
	 * Scales this vector.<br><br>
	 * 
	 * this *= factor
	 * 
	 * @param other
	 * @return This
	 */
	public MutableVector2D scale(double factor) {
		x *= factor;
		y *= factor;
		
		return this;
	}
	
	public MutableVector2D invert() {
		x *= -1;
		y *= -1;
		
		return this;
	}
	
	public DoubleMatrix asMatrix() {
		return new DoubleMatrix(new double[][] {
			{x},
			{y}
		});
	}
	
	/**
	 * Rotates this vector by the specified
	 * angle in radians counter-clockwise.
	 * 
	 * @param angleRad
	 * @return This
	 */
	public MutableVector2D rotateCCW(double angleRad) {
		DoubleMatrix rotationMatrix = new DoubleMatrix(new double[][] {
			{Math.cos(angleRad), -Math.sin(angleRad)},
			{Math.sin(angleRad), Math.cos(angleRad)}
		});
		
		setFrom(rotationMatrix.multiply(asMatrix()));
		
		return this;
	}
	
	public MutableVector2D rotateLeft90() {
		double prevX = x;
		double prevY = y;
		
		x = -prevY;
		y = prevX;
		
		return this;
	}
	
	public double angleDeg() {
		return Math.toDegrees(Math.atan2(y, x));
	}
	
	public double angleRad() {
		return Math.atan2(y, x);
	}
	
	/**
	 * Dots this vector to another one.<br><br>
	 * 
	 * this . otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public double dot(MutableVector2D other) {
		return (x * other.x) + (y * other.y);
	}
	
	public double length() {
		return Math.sqrt((x*x) + (y*y));
	}
	
	public MutableVector2D normalize() {
		x /= length();
		y /= length();
		
		return this;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public MutableVector2D clone() {
		return new MutableVector2D(x, y);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public Vector2D asImmutableVector() {
		return new Vector2D(x, y);
	}
}
