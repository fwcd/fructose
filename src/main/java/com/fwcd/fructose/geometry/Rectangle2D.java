package com.fwcd.fructose.geometry;

import com.fwcd.fructose.draw.DrawGraphics;

public class Rectangle2D extends Polygon2D {
	private static final long serialVersionUID = 7394577888456L;
	
	public Rectangle2D(Vector2D topLeft, double width, double height) {
		this(topLeft, topLeft.add(new Vector2D(width, height)));
	}
	
	public Rectangle2D(double x1, double y1, double x2, double y2) {
		this(new Vector2D(x1, y1), new Vector2D(x2, y2));
	}
	
	public Rectangle2D(Vector2D topLeft, Vector2D bottomRight) {
		super(
				topLeft,
				topLeft.withY(bottomRight.getY()),
				bottomRight,
				topLeft.withX(bottomRight.getX())
		);
	}
	
	public Rectangle2D merge(Rectangle2D other) {
		return new Rectangle2D(absTopLeft().min(other.absTopLeft()), absBottomRight().max(other.absBottomRight()));
	}
	
	/**
	 * @return Always the top-left position, regardless of negative widths
	 */
	public Vector2D absTopLeft() {
		return getTopLeft().min(getBottomRight());
	}
	
	/**
	 * @return Always the top-left position, regardless of negative widths
	 */
	public Vector2D absBottomRight() {
		return getBottomRight().max(getTopLeft());
	}

	public Vector2D getTopLeft() {
		return getVertex(0);
	}
	
	public Vector2D getTopRight() {
		return getTopLeft().withX(getBottomRight().getX());
	}
	
	public Vector2D getBottomLeft() {
		return getBottomRight().withX(getTopLeft().getX());
	}

	public Vector2D getBottomRight() {
		return getVertex(2);
	}
	
	/**
	 * <b>This method has not been tested yet and might contain bugs!</b>
	 * 
	 * <br><br>Checks if this rectangle intersects another.
	 */
	public boolean intersects(Rectangle2D other) {
		Vector2D tl = absTopLeft();
		Vector2D br = absBottomRight();
		Vector2D otherTl = other.absTopLeft();
		Vector2D otherBr = other.absBottomRight();
		
		return tl.getX() < otherBr.getX()
				&& br.getX() > otherTl.getX()
				&& tl.getY() < otherBr.getY()
				&& br.getY() > otherTl.getY();
	}
	
	/**
	 * Provides a quick way to check, whether this
	 * rectangle contains the given point.
	 */
	@Override
	public boolean contains(Vector2D point) {
		Vector2D tl = absTopLeft();
		Vector2D br = absBottomRight();
		
		return point.getX() > tl.getX()
				&& point.getX() < br.getX()
				&& point.getY() > tl.getY()
				&& point.getY() < br.getY();
	}
	
	public double getX1() {
		return getTopLeft().getX();
	}
	
	public double getY1() {
		return getTopLeft().getY();
	}
	
	public double getX2() {
		return getBottomRight().getX();
	}
	
	public double getY2() {
		return getBottomRight().getY();
	}
	
	public double absWidth() {
		return Math.abs(width());
	}
	
	public double absHeight() {
		return Math.abs(height());
	}
	
	public double width() {
		return getX2() - getX1();
	}
	
	public double height() {
		return getY2() - getY1();
	}
	
	public Vector2D getDimensions() {
		return new Vector2D(width(), height());
	}
	
	public Vector2D getCenter() {
		return getTopLeft().add(new Vector2D(width() / 2, height() / 2));
	}

	@Override
	public Rectangle2D getBoundingBox() {
		return this;
	}

	public Rectangle2D movedBy(Vector2D delta) {
		return new Rectangle2D(getTopLeft().add(delta), getBottomRight().add(delta));
	}

	@Override
	public Rectangle2D transformedBy(Matrix transform) {
		return new Rectangle2D(
				new Vector2D(transform.multiply(getTopLeft())),
				new Vector2D(transform.multiply(getBottomRight()))
		);
	}

	public Rectangle2D movedTo(Vector2D topLeft) {
		Vector2D delta = topLeft.sub(getTopLeft());
		return movedBy(delta);
	}

	public Rectangle2D resizedBy(Vector2D delta) {
		return new Rectangle2D(getTopLeft(), getBottomRight().add(delta));
	}

	@Override
	public void draw(DrawGraphics g) {
		double x = getTopLeft().getX();
		double y = getTopLeft().getY();
		double width = width();
		double height = height();
		
		if (width < 0) {
			width *= -1;
			x -= width;
		}
		
		if (height < 0) {
			height *= -1;
			y -= height;
		}
		
		g.drawRect(x, y, width, height);
	}

	@Override
	public void fill(DrawGraphics g) {
		double x = getTopLeft().getX();
		double y = getTopLeft().getY();
		double width = width();
		double height = height();
		
		if (width < 0) {
			width *= -1;
			x -= width;
		}
		
		if (height < 0) {
			height *= -1;
			y -= height;
		}
		
		g.fillRect(x, y, width, height);
	}
}
