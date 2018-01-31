package com.fwcd.fructose.draw;

public interface Drawable {
	void draw(DrawGraphics g);
	
	default void fill(DrawGraphics g) {
		draw(g);
	}
	
	default void drawWith(DrawColor color, DrawGraphics g) {
		DrawColor prevColor = g.getColor();
		g.setColor(color);
		draw(g);
		g.setColor(prevColor);
	}
	
	default void fillWith(DrawColor color, DrawGraphics g) {
		DrawColor prevColor = g.getColor();
		g.setColor(color);
		fill(g);
		g.setColor(prevColor);
	}
}
