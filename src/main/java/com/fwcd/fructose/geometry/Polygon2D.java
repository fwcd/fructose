package com.fwcd.fructose.geometry;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import com.fwcd.fructose.ArrayIterator;
import com.fwcd.fructose.draw.DrawGraphics;
import com.fwcd.fructose.draw.Drawable;

/**
 * An immutable polygon in 2D space.
 * 
 * @author Fredrik
 *
 */
public class Polygon2D implements Shape2D, Serializable, Iterable<Vector2D>, Drawable {
	private static final long serialVersionUID = -4296909373768854502L;
	private final Vector2D[] vertices;
	
	public Polygon2D(Vector2D... vertices) {
		this.vertices = vertices;
	}
	
	public Rectangle2D getBoundingBox() {
		Vector2D topLeft = new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		Vector2D bottomRight = new Vector2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
		
		if (vertices.length < 1) {
			throw new UnsupportedOperationException("Can't fetch a bounding box of a polygon without vertices.");
		}
		
		for (Vector2D vertex : vertices) {
			topLeft = vertex.min(vertex);
			bottomRight = vertex.max(vertex);
		}
		
		return new Rectangle2D(topLeft, bottomRight);
	}
	
	/**
	 * Fetches a sample point that is outside the polygon.
	 */
	private Vector2D outsidePoint() {
		double minX = vertices[0].getX();
		double minY = vertices[0].getY();
		
		for (int i=1; i<vertices.length; i++) {
			Vector2D vertex = vertices[i];
			minX = Math.min(vertex.getX(), minX);
			minY = Math.min(vertex.getY(), minY);
		}
		
		// To address the case where the point (minX, minY)
		// might lie on the border of this polygon:
		return new Vector2D(minX - 1, minY - 1);
	}
	
	/**
	 * <b>This method has not been tested yet and might contain bugs!</b>
	 * 
	 * <br><br>Checks whether this polygon contains the given point
	 * using a ray-casting-algorithm. (Casting a ray from outside
	 * the polygon to the point -> if the number of hits is
	 * even the point is outside the polygon, otherwise it is inside)<br><br>
	 * 
	 * Don't expect this method to run quickly!
	 * 
	 * @param pos - The position to be checked
	 * @return Whether this point is contained by the polygon
	 */
	@Override
	public boolean contains(Vector2D pos) {
		LineSeg2D ray = new LineSeg2D(outsidePoint(), pos);
		
		boolean contained = false;
		
		for (LineSeg2D side : getSides()) {
			if (ray.intersects(side)) {
				contained = !contained;
			}
		}
		
		return contained;
	}

	public int vertices() {
		return vertices.length;
	}
	
	public Vector2D getVertex(int i) {
		return vertices[i];
	}
	
	public Polygon2D transformedBy(Matrix transform) {
		return new Polygon2D(Arrays.stream(vertices)
				.map(transform::multiply)
				.map(Vector2D::new)
				.toArray(Vector2D[]::new)
		);
	}
	
	public LineSeg2D[] getSides() {
		LineSeg2D[] sides = new LineSeg2D[vertices.length];
		
		for (int i=0; i<vertices.length; i++) {
			sides[i] = new LineSeg2D(vertices[i], vertices[(i + 1) % vertices.length]);
		}
		
		return sides;
	}
	
	@Override
	public Iterator<Vector2D> iterator() {
		return new ArrayIterator<>(vertices);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(vertices);
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
		Polygon2D other = (Polygon2D) obj;
		if (!Arrays.equals(vertices, other.vertices)) {
			return false;
		}
		return true;
	}

	@Override
	public void fill(DrawGraphics g) {
		g.fillPolygon(vertices);
	}
	
	@Override
	public void draw(DrawGraphics g) {
		g.drawPolygon(vertices);
	}
}
