package com.fredrikw.fructose.geometry;

import java.io.Serializable;

/**
 * A simple, immutable 2D vector implementation.
 * 
 * @author Fredrik W.
 *
 */
public class Vector2D implements Serializable {
	public static final Vector2D ZERO = new Vector2D(0, 0);
	
	private static final long serialVersionUID = 74865834765873L;
	private final double x;
	private final double y;
	
	public Vector2D(int x, int y) {
		this((double) x, (double) y);
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(float length, float angle, boolean inRadians) {
		double angleRad = inRadians ? angle : Math.toRadians(angle);
		x = Math.cos(angleRad) * length;
		y = Math.sin(angleRad) * length;
	}
	
	public Vector2D(Matrix matrix) {
		if (matrix.getRows() != 2 || matrix.getCols() != 1) {
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
	public Vector2D pointerTo(Vector2D other) {
		return other.sub(this);
	}
	
	public Vector2D add(double x, double y) {
		return new Vector2D(this.x + x, this.y + y);
	}
	
	/**
	 * Adds another vector to this.<br><br>
	 * 
	 * this + otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public Vector2D add(Vector2D other) {
		return new Vector2D(x + other.x, y + other.y);
	}
	
	public Vector2D sub(double x, double y) {
		return new Vector2D(this.x - x, this.y - y);
	}
	
	/**
	 * Subtracts another vector from this.<br><br>
	 * 
	 * this - otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public Vector2D sub(Vector2D other) {
		return new Vector2D(x - other.x, y - other.y);
	}
	
	/**
	 * Scales this vector.<br><br>
	 * 
	 * this * factor
	 * 
	 * @param other
	 * @return This
	 */
	public Vector2D scale(double factor) {
		return new Vector2D(x * factor, y * factor);
	}
	
	public Vector2D scaleX(double factor) {
		return new Vector2D(x * factor, y);
	}
	
	public Vector2D scaleY(double factor) {
		return new Vector2D(x, y * factor);
	}
	
	public Vector2D invert() {
		return new Vector2D(-x, -y);
	}
	
	public Vector2D invertX() {
		return new Vector2D(-x, y);
	}
	
	public Vector2D invertY() {
		return new Vector2D(x, -y);
	}
	
	public Matrix asMatrix() {
		return new Matrix(new double[][] {
			{x},
			{y}
		});
	}
	
	public Vector2D hadamardProduct(Vector2D other) {
		return new Vector2D(x * other.x, y * other.y);
	}
	
	/**
	 * Rotates this vector by the specified
	 * angle in radians counter-clockwise.
	 * 
	 * @param angleRad
	 * @return This
	 */
	public Vector2D rotateCCW(double angleRad) {
		Matrix rotationMatrix = new Matrix(new double[][] {
			{Math.cos(angleRad), -Math.sin(angleRad)},
			{Math.sin(angleRad), Math.cos(angleRad)}
		});
		
		return new Vector2D(rotationMatrix.multiply(asMatrix()));
	}
	
	public Vector2D rotateLeft90() {
		return new Vector2D(-y, x);
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
	public double dot(Vector2D other) {
		return (x * other.x) + (y * other.y);
	}
	
	public double length() {
		return Math.sqrt((x*x) + (y*y));
	}
	
	public Vector2D normalize() {
		return new Vector2D(x / length(), y / length());
	}
	
	public Vector2D min(Vector2D other) {
		return new Vector2D(Math.min(x, other.x), Math.min(y, other.y));
	}
	
	public Vector2D max(Vector2D other) {
		return new Vector2D(Math.max(x, other.x), Math.max(y, other.y));
	}

	public Vector2D withoutX() {
		return new Vector2D(0, y);
	}
	
	public Vector2D withoutY() {
		return new Vector2D(x, 0);
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public Vector2D withX(double x) {
		return new Vector2D(x, y);
	}
	
	public Vector2D withY(double y) {
		return new Vector2D(x, y);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vector2D other = (Vector2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		return true;
	}
	
	public MutableVector2D asMutableVector() {
		return new MutableVector2D(x, y);
	}
}
