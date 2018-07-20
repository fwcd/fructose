package com.fwcd.fructose.geometry;

import java.io.Serializable;

import com.fwcd.fructose.operations.Addable;
import com.fwcd.fructose.operations.Subtractable;

/**
 * A simple, immutable 2D vector implementation.
 * 
 * @author Fredrik
 *
 */
public class Vector2D extends TemplateVector<Vector2D> implements Serializable {
	public static final Vector2D ZERO = new Vector2D(0, 0);
	
	private static final long serialVersionUID = 74865834765873L;
	
	public Vector2D(int x, int y) {
		super(x, y);
	}
	
	public Vector2D(double x, double y) {
		super(x, y);
	}
	
	public Vector2D(double length, double angle, boolean inRadians) {
		this(length, inRadians ? angle : Math.toRadians(angle), (Void) null);
	}
	
	private Vector2D(double length, double angleRad, Void internalConstructorMarker) {
		super(Math.cos(angleRad) * length, Math.sin(angleRad) * length);
	}
	
	public Vector2D(DoubleMatrix matrix) {
		super(
				matrix.getHeight() == 2 || matrix.getWidth() == 1,
				new IllegalArgumentException("Matrix needs to be 1x2 to be converted to a vector: " + matrix.getSize()),
				matrix.get(0, 0),
				matrix.get(0, 1)
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
	public Vector2D pointerTo(Vector2D other) {
		return other.sub(this);
	}
	
	public Vector2D add(double x, double y) {
		return new Vector2D(this.getX() + x, this.getY() + y);
	}
	
	public Vector2D sub(double x, double y) {
		return new Vector2D(this.getX() - x, this.getY() - y);
	}
	
	public Vector2D scaleX(double factor) {
		return new Vector2D(getX() * factor, getY());
	}
	
	public Vector2D scaleY(double factor) {
		return new Vector2D(getX(), getY() * factor);
	}
	
	public Vector2D invertX() {
		return new Vector2D(-getX(), getY());
	}
	
	public Vector2D invertY() {
		return new Vector2D(getX(), -getY());
	}
	
	/**
	 * Rotates this vector by the specified
	 * angle in radians counter-clockwise.
	 * 
	 * @param angleRad
	 * @return This
	 */
	public Vector2D rotateCCW(double angleRad) {
		DoubleMatrix rotationMatrix = new DoubleMatrix(new double[][] {
			{Math.cos(angleRad), -Math.sin(angleRad)},
			{Math.sin(angleRad), Math.cos(angleRad)}
		});
		
		return new Vector2D(rotationMatrix.multiply(asMatrix()));
	}
	
	public Vector2D rotateLeft90() {
		return new Vector2D(-getY(), getX());
	}
	
	public double angleDeg() {
		return Math.toDegrees(Math.atan2(getY(), getX()));
	}
	
	public double angleRad() {
		return Math.atan2(getY(), getX());
	}
	
	@Override
	public double length() {
		return Math.hypot(getX(), getY());
	}

	public Vector2D withoutX() {
		return new Vector2D(0, getY());
	}
	
	public Vector2D withoutY() {
		return new Vector2D(getX(), 0);
	}
	
	public double getX() {
		return get(0);
	}

	public double getY() {
		return get(1);
	}
	
	public Vector2D withX(double x) {
		return new Vector2D(x, getY());
	}
	
	public Vector2D withY(double y) {
		return new Vector2D(getX(), y);
	}
	
	public MutableVector2D asMutableVector() {
		return new MutableVector2D(getX(), getY());
	}

	@Override
	protected Vector2D newInstance(double... data) {
		return new Vector2D(data[0], data[1]);
	}
}
