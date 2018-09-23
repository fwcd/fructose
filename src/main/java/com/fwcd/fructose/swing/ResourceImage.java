package com.fwcd.fructose.swing;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ResourceImage {
	private BufferedImage image;
	
	public ResourceImage(String resourceURL) {
		try {
			image = ImageIO.read(getClass().getResource(resourceURL));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage get() {
		return image;
	}
	
	public ImageIcon getAsIcon() {
		return new ImageIcon(image);
	}
}
