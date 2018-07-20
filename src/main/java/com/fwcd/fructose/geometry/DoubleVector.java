package com.fwcd.fructose.geometry;

import java.io.Serializable;

import com.fwcd.fructose.exception.SizeMismatchException;

/**
 * An immutable, double-valued n-element vector.
 * 
 * <p>Not to be confused with {@link java.util.Vector} (which
 * is a legacy class and should not be used anyway).</p>
 * 
 * @author Fredrik
 *
 */
public class DoubleVector extends TemplateVector<DoubleVector> implements Serializable {
	private static final long serialVersionUID = -6990352776476335167L;
	
	public DoubleVector(double... values) {
		super(values);
	}
	
	@Override
	public double get(int i) {
		return super.get(i);
	}
	
	public Vector2D asVector2D() {
		if (size() != 2) {
			throw new SizeMismatchException("vector size", size(), "vector size", 2);
		}
		
		return new Vector2D(get(0), get(1));
	}
	
	public Vector3D asVector3D() {
		if (size() != 3) {
			throw new SizeMismatchException("vector size", size(), "vector size", 3);
		}
		
		return new Vector3D(get(0), get(1), get(2));
	}

	@Override
	protected DoubleVector newInstance(double... data) {
		return new DoubleVector(data);
	}
}
