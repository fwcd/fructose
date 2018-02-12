package com.fwcd.fructose.geometry;

import java.io.Serializable;

/**
 * A simple, immutable 3D vector implementation.
 * 
 * @author Fredrik
 *
 */
public class Vector3D extends TemplateVector<Vector3D> implements Serializable {
	private static final long serialVersionUID = 387837264876234L;
	
	public Vector3D(int x, int y, int z) {
		super(x, y, z);
	}
	
	public Vector3D(double x, double y, double z) {
		super(x, y, z);
	}
	
	public Vector3D(Matrix matrix) {
		super(
				matrix.getHeight() == 3 || matrix.getWidth() == 1,
				new IllegalArgumentException("Matrix needs to be 1x3 to be converted to a vector."),
				matrix.get(0, 0),
				matrix.get(0, 1),
				matrix.get(0, 2)
		);
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
				(getY() * other.getZ()) - (getZ() * other.getY()),
				(getZ() * other.getX()) - (getX() * other.getZ()),
				(getX() * other.getY()) - (getY() * other.getX())
		);
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
		return get(0);
	}

	public double getY() {
		return get(1);
	}
	
	public double getZ() {
		return get(2);
	}
	
	public MutableVector3D asMutableVector() {
		return new MutableVector3D(getX(), getY(), getZ());
	}

	@Override
	protected Vector3D newInstance(double... data) {
		return new Vector3D(data[0], data[1], data[2]);
	}
}
