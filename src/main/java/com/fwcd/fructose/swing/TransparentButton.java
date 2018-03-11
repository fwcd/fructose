package com.fwcd.fructose.swing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JButton;

public class TransparentButton extends JButton {
	private static final long serialVersionUID = 87648765345L;
	
	private Color standardBG = new Color(0x00000000, true);
	private Color pressedBG = new Color(0x88888888, true);
	private Color hoverBG = new Color(0x00000000, true);

	{
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);

	}

	public TransparentButton() {

	}

	public TransparentButton(Icon icon) {
		super(icon);
	}

	public TransparentButton(String text) {
		super(text);
	}

	public TransparentButton(Icon icon, Runnable onClick) {
		this(icon);
		addClickListener(onClick);
	}

	public TransparentButton(String text, Runnable onClick) {
		this(text);
		addClickListener(onClick);
	}

	private void addClickListener(Runnable onClick) {
		addActionListener(l -> onClick.run());
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(pressedBG);
		} else if (getModel().isRollover()) {
			g.setColor(hoverBG);
		} else {
			g.setColor(standardBG);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
	
	@Override
	public Color getBackground() { return standardBG; }
	
	@Override
	public void setBackground(Color color) { standardBG = color; }
	
	public Color getPressedBackground() { return pressedBG; }
	
	public void setPressedBackground(Color color) { pressedBG = color; }
	
	public Color getHoverBackground() { return hoverBG; }
	
	public void setHoverBackground(Color color) { hoverBG = color; }
}
