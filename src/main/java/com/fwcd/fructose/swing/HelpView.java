package com.fwcd.fructose.swing;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * A viewable text component that
 * is intended to provide helpful
 * information.
 * 
 * @author Fredrik
 *
 */
public class HelpView implements Viewable {
	private final JPanel view;
	private final int padding = 20;
	
	/**
	 * Constructs a new help view.
	 * 
	 * @param text - The lines of text featured in this HelpView
	 */
	public HelpView(String... text) {
		view = new JPanel();
		view.setBorder(new EmptyBorder(padding, padding, padding, padding));
		view.setBackground(Color.WHITE);
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		view.setOpaque(true);
		
		for (String line : text) {
			view.add(new JLabel(" - " + line));
		}
	}

	/**
	 * Displays a new dialog containing this
	 * HelpView.
	 */
	public void showAsDialog() {
		JOptionPane.showMessageDialog(null, view, "Help", JOptionPane.PLAIN_MESSAGE);
	}
	
	@Override
	public JPanel getView() {
		return view;
	}
}
