package com.fwcd.fructose.swing;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;

public class DashedStroke implements Stroke {
	private final Stroke base;
	
	public DashedStroke(int thickness, int dashWidth) {
		base = new BasicStroke(
				thickness,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER,
				dashWidth,
				new float[] {dashWidth},
				0F
		);
	}
	
	@Override
	public Shape createStrokedShape(Shape s) {
		return base.createStrokedShape(s);
	}
}
