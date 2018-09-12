package com.fwcd.fructose.swing;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * An image that gets proportionally
 * resized to fill the entire window.
 * 
 * @author Fredrik
 *
 */
public class FillingImage implements Renderable {
	private Image image;
	
	public FillingImage(Image image) {
		this.image = image;
	}
	
	public FillingImage(String resourceURL) {
		image = new ResourceImage(resourceURL).get();
	}

	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		double imageRatio = (double) imageWidth() / (double) imageHeight();
		double canvasRatio = canvasSize.getWidth() / canvasSize.getHeight();
		
		int width;
		int height;
		
		if (imageRatio > canvasRatio) {
			height = (int) canvasSize.getHeight();
			width = getWidth(height);
		} else {
			width = (int) canvasSize.getWidth();
			height = getHeight(width);
		}
		
		g2d.drawImage(image, 0, 0, width, height, null);
	}
	
	private int getHeight(int width) {
		return (int) ((imageHeight() / (double) imageWidth()) * width);
	}
	
	private int getWidth(int height) {
		return (int) ((imageWidth() / (double) imageHeight()) * height);
	}

	private int imageWidth() {
		return image.getWidth(null);
	}

	private int imageHeight() {
		return image.getHeight(null);
	}

	public Image getImage() {
		return image;
	}
}
