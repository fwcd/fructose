package fwcd.fructose.swing;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * A convenience change listener for swing components
 * that allows lambda-implementations.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface ComponentChangeListener extends ComponentListener {
	void componentChanged(ComponentEvent e);
	
	@Override
	default void componentHidden(ComponentEvent e) {
		componentChanged(e);
	}

	@Override
	default void componentMoved(ComponentEvent e) {
		componentChanged(e);
	}

	@Override
	default void componentResized(ComponentEvent e) {
		componentChanged(e);
	}

	@Override
	default void componentShown(ComponentEvent e) {
		componentChanged(e);
	}
}
