package fwcd.fructose.swing;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @deprecated Use {@link Renderable} instead
 */
@Deprecated
public interface Rendereable {
	void render(Graphics2D g2d, Dimension canvasSize);
}
