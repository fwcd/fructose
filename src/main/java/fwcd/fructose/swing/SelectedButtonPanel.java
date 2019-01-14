package fwcd.fructose.swing;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import fwcd.fructose.Option;

public class SelectedButtonPanel implements View {
	private static final Color INACTIVE_COLOR = Color.WHITE;

	private JComponent view;
	
	private final Color highlightColor;
	private final Map<JButton, Runnable> buttons = new HashMap<>();
	private final List<SelectedButtonPanel> connected = new ArrayList<>();
	private final MouseAdapter mouseAdapter;

	private boolean folding = false;
	private boolean folded = false;
	private Selection selection = new Selection();
	
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
			button.setVisible(selection.matches(button));
		}
		view.repaint();
	}
	
	public void add(JButton button, Runnable onClick) {
		button.setBorderPainted(false);
		button.setOpaque(true);
		button.setBackground(INACTIVE_COLOR);
		button.addActionListener((l) -> select(button));
		button.addMouseListener(mouseAdapter);
		
		buttons.put(button, onClick);
		
		if (selection.isEmpty()) {
			select(button);
		}

		button.setVisible(!folding || selection.matches(button));
		view.add(button);
	}
	
	public void select(JButton button) {
		if (!selection.isEmpty()) {
			JButton selectedButton = selection.getSelectedButton().unwrap();
			selectedButton.setBackground(INACTIVE_COLOR);
			
			if (folded) {
				selectedButton.setVisible(false);
			}
		}
		
		button.setVisible(true);
		button.setBackground(highlightColor);
		
		selection.setSelectedButton(button);
		buttons.get(button).run();
	}
	
	private void syncSelection() {
		for (JButton button : buttons.keySet()) {
			button.setBackground(selection.matches(button) ? highlightColor : INACTIVE_COLOR);
		}
	}
	
	@Override
	public JComponent getComponent() {
		return view;
	}

	public void setOpaque(boolean opaque) {
		view.setOpaque(opaque);
	}
	
	public void connect(SelectedButtonPanel other) {
		connected.add(other);
		other.selection = selection;
		syncSelection();
		other.syncSelection();
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
	
	private static class Selection {
		private Option<JButton> selectedButton = Option.empty();
		
		public void setSelectedButton(JButton selectedButton) { this.selectedButton = Option.of(selectedButton); }
		
		public Option<JButton> getSelectedButton() { return selectedButton; }
		
		public boolean isEmpty() { return !selectedButton.isPresent(); }
		
		public boolean matches(JButton button) { return selectedButton.filter(button::equals).isPresent(); }
	}
}
