package fwcd.fructose.swing;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a simple, "processing-like" way to quickly
 * create a small application that draws something. Thus it
 * allows the user to omit a lot of boilerplate usally required
 * to write Swing applications.
 * 
 * @author Fredrik
 *
 */
public abstract class DrawApp implements Renderable {
	private final PanelFrame view;
	private final RenderPanel panel;
	private final Thread drawThread;
	private final List<Renderable> items = new ArrayList<>();
	
	private int fps = 0;
	private DrawMode mode = DrawMode.OUTLINE;
	
	public DrawApp() {
		panel = new RenderPanel(this);
		view = new PanelFrame(getClass().getSimpleName(), 640, 480, panel);
		setup();
		
		drawThread = new Thread(() -> {
			final long maxFrameTime = 1000 / 60;
			int frames = 0;
			long time = System.currentTimeMillis();
			long frameTime = System.currentTimeMillis();
			
			while (true) {
				long timeDelta = System.currentTimeMillis() - time;
				long frameTimeDelta = System.currentTimeMillis() - frameTime;
				
				if (frameTimeDelta >= maxFrameTime) {
					panel.repaint();
					frames++;
					frameTime = System.currentTimeMillis();
				}
				
				if (timeDelta >= 1000) {
					fps = frames;
					frames = 0;
					time = System.currentTimeMillis();
				}
			}
		}, "Draw Thread");
		drawThread.start();
	}
	
	public int getFPS() {
		return fps;
	}
	
	public void setSize(int width, int height) {
		view.setSize(width, height);
	}
	
	public void mode(DrawMode mode) {
		this.mode = mode;
	}
	
	public Rectangle rect(double x, double y, double width, double height) {
		Rectangle rect = new Rectangle(x, y, width, height);
		items.add(rect);
		return rect;
	}
	
	public Ellipse ellipse(double x, double y, double width, double height) {
		Ellipse ellipse = new Ellipse(x, y, width, height);
		items.add(ellipse);
		return ellipse;
	}
	
	public Line line(double x, double y, double width, double height) {
		Line line = new Line(x, y, width, height);
		items.add(line);
		return line;
	}
	
	public abstract void setup();
	
	public abstract void draw();

	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		draw();
		for (Renderable item : items) {
			item.render(g2d, canvasSize);
		}
	}
	
	protected enum DrawMode {
		FILL, OUTLINE;
	}
	
	protected class Text implements Renderable {
		private String value;
		private int x;
		private int y;
		
		public Text(String value, int x, int y) {
			this.value = value;
			this.x = x;
			this.y = y;
		}

		@Override
		public void render(Graphics2D g2d, Dimension canvasSize) {
			g2d.drawString(value, x, y);
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
	
	protected class Rectangle implements Renderable {
		private int x;
		private int y;
		private int width;
		private int height;
		
		public Rectangle(double x, double y, double width, double height) {
			this.x = (int) x;
			this.y = (int) y;
			this.width = (int) width;
			this.height = (int) height;
		}

		@Override
		public void render(Graphics2D g2d, Dimension canvasSize) {
			switch (mode) {
			
			case FILL:
				g2d.drawRect(x, y, width, height);
				break;
			case OUTLINE:
				g2d.fillRect(x, y, width, height);
				break;
			default:
				break;
			
			}
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}
	
	protected class Line implements Renderable {
		private int startX;
		private int startY;
		private int endX;
		private int endY;

		public Line(double startX, double startY, double endX, double endY) {
			this.startX = (int) startX;
			this.startY = (int) startY;
			this.endX = (int) endX;
			this.endY = (int) endY;
		}

		@Override
		public void render(Graphics2D g2d, Dimension canvasSize) {
			g2d.drawLine(startX, startY, endX, endY);
		}

		public int getStartX() {
			return startX;
		}

		public void setStartX(int startX) {
			this.startX = startX;
		}

		public int getStartY() {
			return startY;
		}

		public void setStartY(int startY) {
			this.startY = startY;
		}

		public int getEndX() {
			return endX;
		}

		public void setEndX(int endX) {
			this.endX = endX;
		}

		public int getEndY() {
			return endY;
		}

		public void setEndY(int endY) {
			this.endY = endY;
		}
	}
	
	protected class Ellipse implements Renderable {
		private int x;
		private int y;
		private int width;
		private int height;
		
		public Ellipse(double x, double y, double radiusX, double radiusY) {
			this.x = (int) x;
			this.y = (int) y;
			this.width = (int) radiusX;
			this.height = (int) radiusY;
		}

		@Override
		public void render(Graphics2D g2d, Dimension canvasSize) {
			switch (mode) {
			
			case FILL:
				g2d.drawOval(x, y, width, height);
				break;
			case OUTLINE:
				g2d.fillOval(x, y, width, height);
				break;
			default:
				break;
			
			}
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}
}
