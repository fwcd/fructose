package fwcd.fructose;

import fwcd.fructose.geometry.Vector2D;

/**
 * A simple, immutable two-dimensional integer vector.
 * 
 * @author Fredrik
 *
 */
public class GridPos {
	private final int x;
	private final int y;

	public GridPos(Vector2D vec) {
		x = (int) vec.getX();
		y = (int) vec.getY();
	}
	
	public GridPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public GridPos add(GridPos other) {
		return add(other.x, other.y);
	}

	public GridPos add(int x, int y) {
		return new GridPos(this.x + x, this.y + y);
	}

	public GridPos sub(GridPos other) {
		return sub(other.x, other.y);
	}

	public GridPos sub(int x, int y) {
		return new GridPos(this.x - x, this.y - y);
	}

	public GridPos scale(double factor) {
		return new GridPos((int) (this.x * factor), (int) (this.y * factor));
	}

	public Vector2D toVec2D() {
		return new Vector2D(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		GridPos other = (GridPos) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}