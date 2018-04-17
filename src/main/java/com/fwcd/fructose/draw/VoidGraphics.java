package com.fwcd.fructose.draw;

import com.fwcd.fructose.geometry.Vector2D;

/**
 * A "fake" graphics class, which is useful when
 * initialization of layouts are required before any
 * actual rendering is performed. <b>Usually one
 * should consider more elegant solutions though!</b>
 * 
 * @author Fredrik
 *
 */
public class VoidGraphics implements DrawGraphics {
	@Override
	public void drawString(String string, double x, double y) {
		
	}

	@Override
	public void drawString(String string, double x, double y, int size) {
		
	}

	@Override
	public DrawColor getColor() {
		return DrawColor.BLACK;
	}

	@Override
	public int getStringWidth(String string) {
		return string.length() * 8;
	}

	@Override
	public int getStringHeight() {
		return 20;
	}

	@Override
	public void setFont(FontStyle style, int size) {
		
	}

	@Override
	public void setColor(DrawColor color) {
		
	}

	@Override
	public void setStroke(StrokeType type, float strokeWidth) {
		
	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2) {
		
	}

	@Override
	public void drawPolygon(Vector2D[] vertices) {
		
	}

	@Override
	public void fillPolygon(Vector2D[] vertices) {
		
	}

	@Override
	public void drawRect(double x, double y, double width, double height) {
		
	}

	@Override
	public void fillRect(double x, double y, double width, double height) {
		
	}

	@Override
	public void drawOval(double centerX, double centerY, double width, double height) {
		
	}

	@Override
	public void fillOval(double centerX, double centerY, double width, double height) {
		
	}
}
