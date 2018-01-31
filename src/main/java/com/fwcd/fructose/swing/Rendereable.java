package com.fwcd.fructose.swing;

import java.awt.Dimension;
import java.awt.Graphics2D;

public interface Rendereable {
	void render(Graphics2D g2d, Dimension canvasSize);
}
