package fwcd.fructose.swing;

import javax.swing.JComponent;

/**
 * A Viewable is an object that can be displayed
 * using a swing component. This could be a
 * view, a view controller, a presenter or similar,
 * depending on which UI architecture is used.
 */
public interface Viewable {
	/**
	 * Fetches the Swing component associated with this viewable.
	 * @return The associated Swing component.
	 */
	JComponent getComponent();
}
