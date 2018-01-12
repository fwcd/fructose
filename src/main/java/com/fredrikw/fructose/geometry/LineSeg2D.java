package com.fredrikw.fructose.geometry;

import com.fredrikw.fructose.draw.DrawGraphics;

/**
 * Represents a line segment with a fixed start
 * and end position. If you need an unbounded line,
 * check out {@link Straight2D}.
 * 
 * @author Fredrik
 *
 */
public class LineSeg2D extends Path2D {
	private static final long serialVersionUID = 29879873324L;
	
	public LineSeg2D(double x1, double y1, double x2, double y2) {
		super(new Vector2D(x1, y1), new Vector2D(x2, y2));
	}
	
	public LineSeg2D(Vector2D start, Vector2D end) {
		super(start, end);
	}
	
	public LineSeg2D movedBy(Vector2D delta) {
		return new LineSeg2D(getStart().add(delta), getEnd().add(delta));
	}

	public Vector2D getStart() {
		return getVertex(0);
	}

	public Vector2D getEnd() {
		return getVertex(1);
	}
	
	/**
	 * Checks whether the slope of the line segment AB is
	 * less than the slope of the line segment AC.
	 */
	private boolean areCCW(Vector2D a, Vector2D b, Vector2D c) {
		return (c.getY() - a.getY()) * (b.getX() - a.getX()) > (b.getY() - a.getY()) * (c.getX() - a.getX());
	}
	
	/**
	 * <b>This method has not been tested yet and might contain bugs!</b>
	 * 
	 * <br><br>Checks whether this line segment intersects with another. <b>Note that
	 * this method might produce wrong results for special cases
	 * like parallel segments.</b>
	 * 
	 * @param other - The other line segment
	 * @return Whether this line intersects
	 */
	public boolean intersects(LineSeg2D other) {
		Vector2D a = getStart();
		Vector2D b = getEnd();
		Vector2D c = other.getStart();
		Vector2D d = other.getEnd();
		
		return areCCW(a, c, d) != areCCW(b, c, d) && areCCW(a, b, c) != areCCW(a, b, d);
	}
	
	@Override
	public String toString() {
		return "Line2D [" + getStart().toString() + " - " + getEnd().toString() + "]";
	}

	public Vector2D getCenter() {
		return new Vector2D(
				(getStart().getX() + getEnd().getX()) / 2,
				(getStart().getY() + getEnd().getY()) / 2
		);
	}

	@Override
	public LineSeg2D transformedBy(Matrix transform) {
		return new LineSeg2D(
				new Vector2D(transform.multiply(getStart())),
				new Vector2D(transform.multiply(getEnd()))
		);
	}

	@Override
	public void draw(DrawGraphics g) {
		Vector2D start = getStart();
		Vector2D end = getEnd();
		
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}
}
