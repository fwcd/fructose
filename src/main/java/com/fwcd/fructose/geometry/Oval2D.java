package com.fwcd.fructose.geometry;

import com.fwcd.fructose.draw.DrawGraphics;
import com.fwcd.fructose.draw.Drawable;

/**
 * An immutable ellipse.
 * 
 * @author Fredrik
 *
 */
public class Oval2D implements Shape2D, Drawable {
	private final Vector2D center;
	private final double width;
	private final double height;
	
	public Oval2D(Vector2D center, double width, double height) {
		this.center = center;
		this.width = width;
		this.height = height;
	}

	public Vector2D getCenter() {
		return center;
	}
	
	public double width() {
		return width;
	}
	
	public double height() {
		return height;
	}
	
	@Override
	public boolean contains(Vector2D pos) {
		Vector2D diff = pos.sub(center);
		return Math.abs(diff.getX()) < (width / 2) && Math.abs(diff.getY()) < (height / 2);
	}

	@Override
	public void fill(DrawGraphics g) {
		g.fillOval(center.getX(), center.getY(), width, height);
	}

	@Override
	public void draw(DrawGraphics g) {
		g.drawOval(center.getX(), center.getY(), width, height);
	}

	public Oval2D movedTo(Vector2D pos) {
		return new Oval2D(pos, width, height);
	}
}
