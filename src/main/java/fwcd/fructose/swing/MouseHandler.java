package fwcd.fructose.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;

import fwcd.fructose.geometry.Vector2D;

public abstract class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
	protected Vector2D posOf(MouseEvent e) {
		return new Vector2D(e.getX(), e.getY());
	}
	
	protected Vector2D screenPosOf(MouseEvent e) {
		return new Vector2D(e.getXOnScreen(), e.getYOnScreen());
	}
	
	public void connect(JComponent component) {
		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
