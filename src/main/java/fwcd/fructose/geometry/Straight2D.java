package fwcd.fructose.geometry;

import java.io.Serializable;

/**
 * Represents an unbounded "line".
 * 
 * @author Fredrik
 *
 */
public class Straight2D implements Serializable {
	private static final long serialVersionUID = 766827367643L;
	private final Vector2D position;
	private final Vector2D direction;
	
	public Straight2D(Vector2D position, Vector2D direction) {
		this.position = position;
		this.direction = direction;
	}

	public Vector2D getSamplePoint() {
		return position.add(direction);
	}
	
	public Vector2D getPerpendicular() {
		return direction.rotateLeft90();
	}
	
	public Vector2D getPosition() {
		return position;
	}

	public Vector2D getDirection() {
		return direction;
	}
}
