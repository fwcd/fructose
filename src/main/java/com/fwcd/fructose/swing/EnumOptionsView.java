package com.fwcd.fructose.swing;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.fwcd.fructose.swing.properties.EnumOptionsProperty;

public class EnumOptionsView implements Viewable {
	private JPanel view = new JPanel();
	
	public EnumOptionsView(boolean horizontal) {
		view.setLayout(new BoxLayout(view, horizontal ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Enum<T>> void bind(EnumOptionsProperty<T> property) {
		view.removeAll();
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		for (Enum<T> constant : property.get().getClass().getEnumConstants()) {
			JRadioButton radioButton = new JRadioButton();
			
			if (property.get().equals(constant)) {
				radioButton.setSelected(true);
			}
			
			radioButton.setText(constant.toString());
			radioButton.addChangeListener((l) -> {
				if (radioButton.isSelected()) {
					property.set((T) constant);
				}
			});
			
			buttonGroup.add(radioButton);
			view.add(radioButton);
		}
	}
	
	@Override
	public JComponent getView() {
		return view;
	}
}
