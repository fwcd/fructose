package com.fredrikw.fructose.swing;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ResourceImage {
	private Image image;
	
	public ResourceImage(String resourceURL) {
		try {
			image = ImageIO.read(getClass().getResource(resourceURL));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image get() {
		return image;
	}
	
	public ImageIcon getAsIcon() {
		return new ImageIcon(image);
	}
}
