package com.fwcd.fructose.swing;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * An image that gets tiled to
 * fill the entire window.
 * 
 * @author Fredrik
 *
 */
public class Tile implements Rendereable {
	private Image image;
	
	public Tile(String resourcePath) {
		image = new ResourceImage(resourcePath).get();
	}
	
	public int getWidth() {
		return image.getWidth(null);
	}
	
	public int getHeight() {
		return image.getHeight(null);
	}
	
	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		for (int y=0; y<canvasSize.getHeight(); y+=getHeight()) {
			for (int x=0; x<canvasSize.getWidth(); x+=getWidth()) {
				g2d.drawImage(image, x, y, null);
			}
		}
	}
}
