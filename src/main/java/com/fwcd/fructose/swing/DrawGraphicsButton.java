package com.fwcd.fructose.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class DrawGraphicsButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	public DrawGraphicsButton(Dimension size, Rendereable... rendereables) {
		setPreferredSize(size);
		
		Image image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setColor(Color.BLACK);
		
		for (Rendereable rendereable : rendereables) {
			rendereable.render(g2d, size);
		}
		
		setIcon(new ImageIcon(image));
	}
}
