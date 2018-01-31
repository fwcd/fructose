package com.fwcd.fructose.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * An image wrapper component.
 * 
 * @author Fredrik
 *
 */
public class ImageView implements Viewable, Rendereable {
	private JPanel view;
	private Image image;
	private List<Runnable> listeners = new ArrayList<>();
	
	private final int width;
	private final int height;
	
	public ImageView(int width, int height) {
		this(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), width, height);
	}
	
	public ImageView(String resourceURL) {
		this(new ResourceImage(resourceURL).get());
	}
	
	public ImageView(String resourceURL, int width, int height) {
		this(new ResourceImage(resourceURL).get(), width, height);
	}
	
	public ImageView(Image image) {
		this(image, image.getWidth(null), image.getHeight(null));
	}
	
	public ImageView(Image image, int width, int height) {
		this.image = image;
		
		this.width = width;
		this.height = height;
		
		view = new RenderPanel(this);
		
		view.setOpaque(false);
		view.setPreferredSize(new Dimension(width, height));
		view.setLayout(new BorderLayout());
	}
	
	public List<Runnable> getChangeListeners() {
		return listeners;
	}
	
	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		if (image == null) {
			g2d.setColor(Color.GRAY);
			g2d.fillRect(0, 0, width, height);
		}else {
			g2d.drawImage(image, 0, 0, width, height, null);
		}
	}
	
	@Override
	public JPanel getView() {
		return view;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}
}
