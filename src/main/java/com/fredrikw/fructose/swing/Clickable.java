package com.fredrikw.fructose.swing;

import com.fredrikw.fructose.geometry.Polygon2D;
import com.fredrikw.fructose.geometry.Vector2D;

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
