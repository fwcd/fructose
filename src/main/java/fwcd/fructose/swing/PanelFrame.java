package fwcd.fructose.swing;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

/**
 * A JFrame containing only one component.
 * 
 * @author Fredrik
 *
 */
public class PanelFrame extends JFrame {
	private static final long serialVersionUID = 874934857L;
	
	@Deprecated
	public PanelFrame(String title, int width, int height, Viewable viewable) {
		this(title, width, height, viewable.getComponent());
	}
	
	public PanelFrame(String title, int width, int height, View view) {
		this(title, width, height, view.getComponent());
	}
	
	public PanelFrame(String title, int width, int height, Component component) {
		this(title, width, height, component, false);
	}
	
	public PanelFrame(String title, int width, int height, Component component, boolean undecorated) {
		super(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setUndecorated(undecorated);
		
		add(component, BorderLayout.CENTER);
		
		setVisible(true);
	}
}
