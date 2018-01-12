package com.fredrikw.fructose.draw;

import com.fredrikw.fructose.geometry.Vector2D;

public interface DrawGraphics {
	void drawString(String string, double x, double y);
	
	void drawString(String string, double x, double y, int size);
	
	DrawColor getColor();
	
	int getStringWidth(String string);
	
	int getStringHeight();
	
	void setFont(FontStyle style, int size);
	
	void setColor(DrawColor color);
	
	void setStroke(StrokeType type, float strokeWidth);
	
	void drawLine(double x1, double y1, double x2, double y2);
	
	void drawPolygon(Vector2D[] vertices);
	
	void fillPolygon(Vector2D[] vertices);
	
	void drawRect(double x, double y, double width, double height);
	
	void fillRect(double x, double y, double width, double height);
	
	void drawOval(double centerX, double centerY, double width, double height);
	
	void fillOval(double centerX, double centerY, double width, double height);
}
