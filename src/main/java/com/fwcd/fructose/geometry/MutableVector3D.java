package com.fwcd.fructose.geometry;

/**
 * A simple, mutable 3D vector implementation.
 * 
 * @author Fredrik
 *
 */
public class MutableVector3D implements Cloneable {
	private double x;
	private double y;
	private double z;
	
	public MutableVector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public MutableVector3D(Matrix matrix) {
		setFrom(matrix);
	}

	public void setFrom(Matrix matrix) {
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
	public MutableVector3D pointerTo(MutableVector3D other) {
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
	public MutableVector3D add(MutableVector3D other) {
		x += other.x;
		y += other.y;
		z += other.z;
		
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
	public MutableVector3D sub(MutableVector3D other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
		
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
	public MutableVector3D scale(double factor) {
		x *= factor;
		y *= factor;
		z *= factor;
		
		return this;
	}
	
	public MutableVector3D invert() {
		x *= -1;
		y *= -1;
		z *= -1;
		
		return this;
	}
	
	/**
	 * Dots this vector to another one.<br><br>
	 * 
	 * this . otherVec
	 * 
	 * @param other
	 * @return This
	 */
	public double dot(MutableVector3D other) {
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
	public MutableVector3D cross(MutableVector3D other) {
		return new MutableVector3D(
				(y * other.z) - (z * other.y),
				(z * other.x) - (x * other.z),
				(x * other.y) - (y * other.x)
		);
	}
	
	public double length() {
		return Math.sqrt((x*x) + (y*y) + (z*z));
	}
	
	public MutableVector3D normalize() {
		x /= length();
		y /= length();
		z /= length();
		
		return this;
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
	public MutableVector3D rotateXAxisCCW(double angleRad) {
		Matrix rotationMatrix = new Matrix(new double[][] {
			{1, 0, 0},
			{0, Math.cos(angleRad), -Math.sin(angleRad)},
			{0, Math.sin(angleRad), Math.cos(angleRad)}
		});
		
		setFrom(rotationMatrix.multiply(asMatrix()));
		
		return this;
	}
	
	/**
	 * Rotates this vector around the y-axis by the specified
	 * angle in radians counter-clockwise.
	 * 
	 * @param angleRad
	 * @return This
	 */
	public MutableVector3D rotateYAxisCCW(double angleRad) {
		Matrix rotationMatrix = new Matrix(new double[][] {
			{Math.cos(angleRad), 0, Math.sin(angleRad)},
			{0, 1, 0},
			{-Math.sin(angleRad), 0, Math.cos(angleRad)}
		});
		
		setFrom(rotationMatrix.multiply(asMatrix()));
		
		return this;
	}
	
	/**
	 * Rotates this vector around the z-axis by the specified
	 * angle in radians counter-clockwise.
	 * 
	 * @param angleRad
	 * @return This
	 */
	public MutableVector3D rotateZAxisCCW(double angleRad) {
		Matrix rotationMatrix = new Matrix(new double[][] {
			{Math.cos(angleRad), -Math.sin(angleRad), 0},
			{Math.sin(angleRad), Math.cos(angleRad), 0},
			{0, 0, 1}
		});
		
		setFrom(rotationMatrix.multiply(asMatrix()));
		
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
	
	public double getZ() {
		return z;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	@Override
	public MutableVector3D clone() {
		return new MutableVector3D(x, y, z);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	public Vector3D asImmutableVector() {
		return new Vector3D(x, y, z);
	}
}
