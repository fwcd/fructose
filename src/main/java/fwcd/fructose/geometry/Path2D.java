package fwcd.fructose.geometry;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import fwcd.fructose.ArrayIterator;
import fwcd.fructose.draw.DrawGraphics;
import fwcd.fructose.draw.Drawable;

public class Path2D implements Serializable, Iterable<Vector2D>, Drawable {
	private static final long serialVersionUID = -232398798347L;
	private final Vector2D[] vertices;
	
	public Path2D(Vector2D... vertices) {
		this.vertices = vertices;
	}
	
	public int vertices() {
		return vertices.length;
	}
	
	public Vector2D getVertex(int i) {
		return vertices[i];
	}
	
	public Path2D transformedBy(DoubleMatrix transform) {
		return new Path2D(Arrays.stream(vertices)
				.map(transform::multiply)
				.map(Vector2D::new)
				.toArray(Vector2D[]::new)
		);
	}

	public double length() {
		double length = 0;
		
		for (int i=0; i<vertices.length-1; i++) {
			length += vertices[i + 1].sub(vertices[i]).length();
		}
		
		return length;
	}
	
	@Override
	public Iterator<Vector2D> iterator() {
		return new ArrayIterator<>(vertices);
	}

	@Override
	public void draw(DrawGraphics g) {
		for (int i=0; i<vertices.length-1; i++) {
			Vector2D start = vertices[i];
			Vector2D end = vertices[i + 1];
			g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
		}
	}
}
