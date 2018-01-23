package com.fredrikw.fructose.geometry;

import java.io.Serializable;

/**
 * A simple, immutable 3D vector implementation.
 * 
 * @author Fredrik W.
 *
 */
public class Vector3D implements Serializable {
	private static final long serialVersionUID = 387837264876234L;
	private final double x;
	private final double y;
	private final double z;
	
	public Vector3D(int x, int y, int z) {
		this((double) x, (double) y, (double) z);
	}
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Matrix matrix) {
		if (matrix.getHeight() != 3 || matrix.getWidth() != 1) {
			throw new RuntimeException("Matrix needs to be 1x3 to be converted to a vector.");
		}
		
		x = matrix.get(0, 0);
		y = matrix.get(0, 1);
		z = matrix.get(0, 2);
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
	public Vector3D pointerTo(Vector3D other) {
		return other.sub(this);
	}
	
	/**
	 * Adds another vector to this.<br><br>
	 * 
	 * this + otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public Vector3D add(Vector3D other) {
		return new Vector3D(x + other.x, y + other.y, z + other.z);
	}
	
	/**
	 * Subtracts another vector from this.<br><br>
	 * 
	 * this - otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public Vector3D sub(Vector3D other) {
		return new Vector3D(x - other.x, y - other.y, z - other.z);
	}
	
	/**
	 * Scales this vector.<br><br>
	 * 
	 * this * factor
	 * 
	 * @param other
	 * @return This
	 */
	public Vector3D scale(double factor) {
		return new Vector3D(x * factor, y * factor, z * factor);
	}
	
	public Vector3D invert() {
		return new Vector3D(x * -1, y * -1, z * -1);
	}
	
	public Vector3D hadamardProduct(Vector3D other) {
		return new Vector3D(x * other.x, y * other.y, z * other.z);
	}
	
	/**
	 * Dots this vector to another one.<br><br>
	 * 
	 * this . otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public double dot(Vector3D other) {
		return (x * other.x) + (y * other.y) + (z * other.z);
	}
	
	/**
	 * Returns the cross product of this and
	 * another specified vector.<br><br>
	 * 
	 * this x otherVec
	 * 
	 * @param other - The other 3D vector
	 * @return The orthogonal vector
	 */
	public Vector3D cross(Vector3D other) {
		return new Vector3D(
				(y * other.z) - (z * other.y),
				(z * other.x) - (x * other.z),
				(x * other.y) - (y * other.x)
		);
	}
	
	public double length() {
		return Math.sqrt((x*x) + (y*y) + (z*z));
	}
	
	public Vector3D normalize() {
		return new Vector3D(x / length(), y / length(), z / length());
	}
	
	public Matrix asMatrix() {
		return new Matrix(new double[][] {
			{x},
			{y},
			{z}
		});
	}
	
	/**
	 * Rotates this vector around the x-axis by the specified
	 * angle in radians counter-clockwise.
	 * 
	 * @param angleRad
	 * @return This
	 */
	public Vector3D rotateXAxisCCW(double angleRad) {
		Matrix rotationMatrix = new Matrix(new double[][] {
			{1, 0, 0},
			{0, Math.cos(angleRad), -Math.sin(angleRad)},
			{0, Math.sin(angleRad), Math.cos(angleRad)}
		});
		
		return new Vector3D(rotationMatrix.multiply(asMatrix()));
	}
	
	/**
	 * Rotates this vector around the y-axis by the specified
	 * angle in radians counter-clockwise.
	 * 
	 * @param angleRad
	 * @return This
	 */
	public Vector3D rotateYAxisCCW(double angleRad) {
		Matrix rotationMatrix = new Matrix(new double[][] {
			{Math.cos(angleRad), 0, Math.sin(angleRad)},
			{0, 1, 0},
			{-Math.sin(angleRad), 0, Math.cos(angleRad)}
		});
		
		return new Vector3D(rotationMatrix.multiply(asMatrix()));
	}
	
	/**
	 * Rotates this vector around the z-axis by the specified
	 * angle in radians counter-clockwise.
	 * 
	 * @param angleRad
	 * @return This
	 */
	public Vector3D rotateZAxisCCW(double angleRad) {
		Matrix rotationMatrix = new Matrix(new double[][] {
			{Math.cos(angleRad), -Math.sin(angleRad), 0},
			{Math.sin(angleRad), Math.cos(angleRad), 0},
			{0, 0, 1}
		});
		
		return new Vector3D(rotationMatrix.multiply(asMatrix()));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
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
		temp = Double.doubleToLongBits(z);
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
		Vector3D other = (Vector3D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) {
			return false;
		}
		return true;
	}
	
	public MutableVector3D asMutableVector() {
		return new MutableVector3D(x, y, z);
	}
}
