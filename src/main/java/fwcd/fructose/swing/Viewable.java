package fwcd.fructose.swing;

import javax.swing.JComponent;

/**
 * A Viewable is an object that can be displayed
 * using a swing component.
 *
 * @deprecated Use {@link View} instead
 */
@Deprecated
public interface Viewable {
	JComponent getView();
}
