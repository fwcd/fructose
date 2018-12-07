package fwcd.fructose.geometry;

public class Square2D extends Rectangle2D {
	private static final long serialVersionUID = -12435345345L;
	
	public Square2D(Vector2D topLeft, double sideLength) {
		super(topLeft, sideLength, sideLength);
	}
	
	@Override
	public Square2D movedBy(Vector2D delta) {
		return new Square2D(getTopLeft().add(delta), width());
	}

	@Override
	public Square2D transformedBy(DoubleMatrix transform) {
		// TODO: Implement proportional square transforms
		return this;
	}

	@Override
	public Square2D movedTo(Vector2D topLeft) {
		return new Square2D(topLeft, width());
	}
}
