package com.fwcd.fructose.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Renderable[] onRender;
	
	public RenderPanel(Renderable... onRender) {
		this.onRender = onRender;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Renderable Renderable : onRender) {
			Renderable.render((Graphics2D) g, getSize());
		}
	}
}
