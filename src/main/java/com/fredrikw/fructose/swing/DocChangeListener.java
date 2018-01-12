package com.fredrikw.fructose.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Convience class to allow lambda-implementations of {@link DocumentListener}.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface DocChangeListener extends DocumentListener {
	void onChange(DocumentEvent e);
	
	@Override
	default void insertUpdate(DocumentEvent e) {
		onChange(e);
	}

	@Override
	default void removeUpdate(DocumentEvent e) {
		onChange(e);
	}

	@Override
	default void changedUpdate(DocumentEvent e) {
		onChange(e);
	}
}
