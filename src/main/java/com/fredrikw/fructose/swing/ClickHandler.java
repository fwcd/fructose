package com.fredrikw.fructose.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.fredrikw.fructose.geometry.Vector2D;

/**
 * A click handler that automatically directs mouse
 * actions to the respective clickable objects.
 * 
 * @author Fredrik
 *
 */
public class ClickHandler extends MouseAdapter {
	private Collection<Clickable> clickables;
	
	public ClickHandler() {
		clickables = new ArrayList<>();
	}
	
	public ClickHandler(Clickable... clickables) {
		this.clickables = Arrays.asList(clickables);
	}
	
	public ClickHandler(Collection<Clickable> clickables) {
		this.clickables = clickables;
	}
	
	public void add(Clickable clickable) {
		clickables.add(clickable);
	}

	private Vector2D vecOf(MouseEvent e) {
		return new Vector2D(e.getX(), e.getY());
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Vector2D pos = vecOf(e);
		
		for (Clickable clickable : clickables) {
			clickable.onMouseDown(pos);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2D pos = vecOf(e);
		
		for (Clickable clickable : clickables) {
			clickable.onMouseDrag(pos);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vector2D pos = vecOf(e);
		
		for (Clickable clickable : clickables) {
			clickable.onMouseUp(pos);
		}
	}
}
