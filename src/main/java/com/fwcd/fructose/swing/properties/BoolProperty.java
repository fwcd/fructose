package com.fwcd.fructose.swing.properties;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToggleButton;

import com.fwcd.fructose.properties.BasicProperty;

public class BoolProperty extends BasicProperty<Boolean, JToggleButton> {
	public BoolProperty(boolean initialState) {
		set(initialState);
	}
	
	public void bind(JCheckBoxMenuItem component) {
		component.setSelected(get());
		component.addChangeListener((l) -> set(component.isSelected()));
	}
	
	@Override
	public void bind(JToggleButton component) {
		component.setSelected(get());
		component.addChangeListener((l) -> set(component.isSelected()));
	}

	public void invert() {
		set(!get());
	}
}
