package fwcd.fructose.swing;

import fwcd.fructose.geometry.Polygon2D;
import fwcd.fructose.geometry.Vector2D;

/**
 * A clickable item.
 * 
 * @author Fredrik
 *
 */
public interface Clickable {
	Polygon2D getClickBox();
	
	void onMouseDown(Vector2D pos);
	
	void onMouseDrag(Vector2D pos);
	
	void onMouseUp(Vector2D pos);
}
