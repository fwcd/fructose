package com.fwcd.fructose.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Rendereable[] onRender;
	
	public RenderPanel(Rendereable... onRender) {
		this.onRender = onRender;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Rendereable rendereable : onRender) {
			rendereable.render((Graphics2D) g, getSize());
		}
	}
}
