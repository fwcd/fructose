package fwcd.fructose.swing;

import java.awt.Graphics2D;

import fwcd.fructose.draw.Drawable;
import fwcd.fructose.geometry.LineSeg2D;
import fwcd.fructose.geometry.Rectangle2D;
import fwcd.fructose.geometry.Vector2D;

/**
 * A class containing static drawing methods for
 * geometric objects in swing.
 * 
 * @deprecated Replaced by the {@link Drawable} API
 * @author Fredrik
 *
 */
@Deprecated
public class DrawUtils {
	public static void draw(LineSeg2D line, Graphics2D g2d) {
		g2d.drawLine(
				(int) line.getStart().getX(),
				(int) line.getStart().getY(),
				(int) line.getEnd().getX(),
				(int) line.getEnd().getY()
		);
	}
	
	public static void draw(Rectangle2D rect, Graphics2D g2d) {
		Vector2D tl = rect.absTopLeft();
		int x = (int) tl.getX();
		int y = (int) tl.getY();
		int width = (int) rect.absWidth();
		int height = (int) rect.absHeight();
		
		g2d.drawRect(x, y, width, height);
	}
	
	public static void fill(Rectangle2D rect, Graphics2D g2d) {
		Vector2D tl = rect.absTopLeft();
		int x = (int) tl.getX();
		int y = (int) tl.getY();
		int width = (int) rect.absWidth();
		int height = (int) rect.absHeight();
		
		g2d.fillRect(x, y, width, height);
	}
}
