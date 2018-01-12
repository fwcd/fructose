package com.fredrikw.fructose.swing;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class SelectedButtonPanel implements Viewable {
	private JComponent view;
	
	private final Color highlightColor;
	private final Map<JButton, Runnable> buttons = new HashMap<>();
	private final MouseAdapter mouseAdapter;

	private boolean folding = false;
	private boolean folded = false;
	private JButton selectedButton = null;
	
	public SelectedButtonPanel(boolean horizontal, Color highlightColor) {
		this(horizontal, highlightColor, false);
	}
	
	public SelectedButtonPanel(boolean horizontal, Color highlightColor, boolean asToolBar) {
		this.highlightColor = highlightColor;
		
		if (asToolBar) {
			view = new JToolBar(horizontal ? JToolBar.HORIZONTAL : JToolBar.VERTICAL);
		} else {
			view = new JPanel();
			view.setLayout(new BoxLayout(view, horizontal ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));
		}
		
		mouseAdapter = new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				if (folding) {
					unfold();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (folding) {
					fold();
				}
			}
			
		};
		
		view.setOpaque(false);
		view.addMouseListener(mouseAdapter);
		
		if (folding) {
			fold();
		} else {
			unfold();
		}
	}
	
	private void unfold() {
		folded = false;
		for (JButton button : buttons.keySet()) {
			button.setVisible(true);
		}
		view.repaint();
	}
	
	private void fold() {
		folded = true;
		for (JButton button : buttons.keySet()) {
			button.setVisible(button.equals(selectedButton));
		}
		view.repaint();
	}
	
	public void add(JButton button, Runnable onClick) {
		button.setBorderPainted(false);
		button.setOpaque(true);
		button.setBackground(Color.WHITE);
		button.addActionListener((l) -> select(button));
		button.addMouseListener(mouseAdapter);
		
		buttons.put(button, onClick);
		
		if (selectedButton == null) {
			select(button);
		}

		button.setVisible(!folding || button.equals(selectedButton));
		view.add(button);
	}
	
	public void select(JButton button) {
		if (selectedButton != null) {
			selectedButton.setBackground(Color.WHITE);
			
			if (folded) {
				selectedButton.setVisible(false);
			}
		}
		
		button.setVisible(true);
		button.setBackground(highlightColor);
		
		selectedButton = button;
		buttons.get(button).run();
	}
	
	@Override
	public JComponent getView() {
		return view;
	}

	public void setOpaque(boolean opaque) {
		view.setOpaque(opaque);
	}

	public void setFloatable(boolean floatable) {
		if (view instanceof JToolBar) {
			((JToolBar) view).setFloatable(floatable);
		} else {
			throw new UnsupportedOperationException("Can't set a floatable property on a non-toolbar SelectedButtonPanel!");
		}
	}
	
	public void setFolding(boolean folding) {
		this.folding = folding;
		
		if (folding) {
			fold();
		} else {
			unfold();
		}
	}
}
