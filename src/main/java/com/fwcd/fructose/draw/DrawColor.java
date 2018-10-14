package com.fwcd.fructose.draw;

import java.io.Serializable;

/**
 * Represents a 32-bit color with alpha.
 * 
 * @author Fredrik
 *
 */
public class DrawColor implements Serializable {
	private static final long serialVersionUID = -471952756873991915L;
	public static final DrawColor WHITE = new DrawColor(255, 255, 255);
	public static final DrawColor BLACK = new DrawColor(0, 0, 0);
	public static final DrawColor RED = new DrawColor(255, 0, 0);
	public static final DrawColor GREEN = new DrawColor(0, 255, 0);
	public static final DrawColor BLUE = new DrawColor(0, 0, 255);
	public static final DrawColor YELLOW = new DrawColor(255, 255, 0);
	public static final DrawColor ORANGE = new DrawColor(255, 200, 0);
	public static final DrawColor DARK_GRAY = new DrawColor(64, 64, 64);
	public static final DrawColor GRAY = new DrawColor(128, 128, 128);
	public static final DrawColor LIGHT_GRAY = new DrawColor(192, 192, 192);
	public static final DrawColor MAGENTA = new DrawColor(255, 0, 255);
	
	private final int r; // Holds an unsigned byte
	private final int g; // Holds an unsigned byte
	private final int b; // Holds an unsigned byte
	private final int a; // Holds an unsigned byte
	
	public DrawColor(java.awt.Color awtColor) {
		this(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), awtColor.getAlpha());
	}
	
	public DrawColor(int rgb) {
		this((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
	}
	
	public DrawColor(int rgba, boolean alphaInSmallestBits) {
		if (alphaInSmallestBits) {
			r = (rgba >> 24) & 0xFF;
			g = (rgba >> 16) & 0xFF;
			b = (rgba >> 8) & 0xFF;
			a = rgba & 0xFF;
		} else {
			a = (rgba >> 24) & 0xFF;
			r = (rgba >> 16) & 0xFF;
			g = (rgba >> 8) & 0xFF;
			b = rgba & 0xFF;
		}
	}
	
	public DrawColor(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public DrawColor(int r, int g, int b) {
		this(r, g, b, 255);
	}
	
	public DrawColor brighter() {
		return new DrawColor(r + (r / 2), g + (g / 2), b + (b / 2));
	}
	
	public DrawColor darker() {
		return new DrawColor(r - (r / 2), g - (g / 2), b - (b / 2));
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public int getAlpha() {
		return a;
	}
	
	public int getRGB() {
		return ((r << 16) & 0xFF) | ((g << 8) & 0xFF) | (b & 0xFF);
	}
	
	public int getARGB() {
		return ((a << 24) & 0xFF) | ((r << 16) & 0xFF) | ((g << 8) & 0xFF) | (b & 0xFF);
	}
	
	public int getRGBA() {
		return ((r << 24) & 0xFF) | ((g << 16) & 0xFF) | ((b << 8) & 0xFF) | (a & 0xFF);
	}
	
	public java.awt.Color asAWTColor() {
		return new java.awt.Color(r, g, b, a);
	}
	
	@Override
	public String toString() {
		return "[#" + Integer.toHexString(getRGB()) + " - alpha: " + Integer.toString(a) + "]";
	}
}
