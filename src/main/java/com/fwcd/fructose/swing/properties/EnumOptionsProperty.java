package com.fwcd.fructose.swing.properties;

import com.fwcd.fructose.properties.BasicProperty;
import com.fwcd.fructose.swing.EnumOptionsView;

public class EnumOptionsProperty<T extends Enum<T>> extends BasicProperty<T, EnumOptionsView> {
	public EnumOptionsProperty(T selectedValue) {
		set(selectedValue);
	}
	
	@Override
	public void bind(EnumOptionsView component) {
		component.bind(this);
	}
}
