package com.fredrikw.fructose.swing;

import javax.swing.JComponent;

/**
 * A Viewable is an object that can be displayed
 * using a swing component.
 *
 */
public interface Viewable {
	JComponent getView();
}
