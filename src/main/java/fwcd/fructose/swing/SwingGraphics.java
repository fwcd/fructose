package fwcd.fructose.swing;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Arrays;

import fwcd.fructose.draw.DrawColor;
import fwcd.fructose.draw.DrawGraphics;
import fwcd.fructose.draw.FontStyle;
import fwcd.fructose.draw.StrokeType;
import fwcd.fructose.geometry.Vector2D;

public class SwingGraphics implements DrawGraphics {
	private final Graphics2D g2d;
	
	public SwingGraphics(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void drawString(String string, double x, double y) {
		g2d.drawString(string, (int) x, (int) y);
	}

	@Override
	public void setStroke(StrokeType type, float strokeWidth) {
		switch (type) {
		
		case DASHED:
			g2d.setStroke(new DashedStroke((int) strokeWidth, 5));
			break;
		case LINE:
			g2d.setStroke(new BasicStroke(strokeWidth));
			break;
		default:
			throw new IllegalArgumentException("Unsupported stroke type.");
		
		}
	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2) {
		g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	private Polygon getAWTPolygon(Vector2D[] vertices) {
		int[] xPoints = Arrays.stream(vertices).mapToInt(v -> (int) v.getX()).toArray();
		int[] yPoints = Arrays.stream(vertices).mapToInt(v -> (int) v.getY()).toArray();
		return new Polygon(xPoints, yPoints, vertices.length);
	}
	
	@Override
	public void drawPolygon(Vector2D[] vertices) {
		g2d.drawPolygon(getAWTPolygon(vertices));
	}

	@Override
	public void fillPolygon(Vector2D[] vertices) {
		g2d.fillPolygon(getAWTPolygon(vertices));
	}

	@Override
	public void drawRect(double x, double y, double width, double height) {
		g2d.drawRect((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public void fillRect(double x, double y, double width, double height) {
		g2d.fillRect((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public void drawOval(double x, double y, double width, double height) {
		g2d.drawOval((int) (x - (width / 2)), (int) (y - (height / 2)), (int) width, (int) height);
	}

	@Override
	public void fillOval(double x, double y, double width, double height) {
		g2d.fillOval((int) (x - (width / 2)), (int) (y - (height / 2)), (int) width, (int) height);
	}

	@Override
	public void setColor(DrawColor color) {
		g2d.setColor(color.asAWTColor());
	}

	@Override
	public DrawColor getColor() {
		return new DrawColor(g2d.getColor());
	}

	@Override
	public int getStringWidth(String string) {
		return g2d.getFontMetrics().stringWidth(string);
	}

	@Override
	public int getStringHeight() {
		return g2d.getFontMetrics().getHeight();
	}

	@Override
	public void setFont(FontStyle style, int size) {
		int awtStyle = -1;
		
		switch (style) {
		
		case BOLD:
			awtStyle = Font.BOLD;
			break;
		case ITALIC:
			awtStyle = Font.ITALIC;
			break;
		case PLAIN:
			awtStyle = Font.PLAIN;
			break;
		default:
			throw new IllegalArgumentException("Invalid font style.");
		
		}
		
		g2d.setFont(new Font(g2d.getFont().getFontName(), awtStyle, size));
	}

	@Override
	public void drawString(String string, double x, double y, int size) {
		Font prevFont = g2d.getFont();
		g2d.setFont(new Font(g2d.getFont().getFontName(), prevFont.getStyle(), size));
		g2d.drawString(string, (int) x, (int) y);
		g2d.setFont(prevFont);
	}
}
