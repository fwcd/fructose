package com.fredrikw.fructose.swing.properties;

import com.fredrikw.fructose.properties.BasicProperty;
import com.fredrikw.fructose.swing.EnumOptionsView;

public class EnumOptionsProperty<T extends Enum<T>> extends BasicProperty<T, EnumOptionsView> {
	public EnumOptionsProperty(T selectedValue) {
		set(selectedValue);
	}
	
	@Override
	public void bind(EnumOptionsView component) {
		component.bind(this);
	}
}
