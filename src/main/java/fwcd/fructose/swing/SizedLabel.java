package fwcd.fructose.swing;

import java.awt.Font;

import javax.swing.JLabel;

public class SizedLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	
	public SizedLabel(int size) {
		this(size, Font.PLAIN);
	}
	
	public SizedLabel(int size, int fontStyle) {
		setFont(size, fontStyle);
	}
	
	public SizedLabel(String text) {
		super(text);
	}
	
	public SizedLabel(String text, int size) {
		this(text, size, Font.PLAIN);
	}
	
	public SizedLabel(String text, int size, int fontStyle) {
		super(text);
		setFont(size, fontStyle);
	}
	
	public void setFont(int size, int fontStyle) {
		setFont(new Font(getFont().getFontName(), fontStyle, size));
	}
}
