package com.fwcd.fructose.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import com.fwcd.fructose.GridPos;

/**
 * A flexible, but much easier to use GridBagLayout.
 * 
 * @author Fredrik
 *
 */
public class DynamicGridLayout extends GridBagLayout {
	private static final long serialVersionUID = 1L;

	private boolean stretchToFill;
	
	public DynamicGridLayout(boolean strechToFill) {
		this.stretchToFill = strechToFill;
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints != null && constraints instanceof GridPos) {
			GridPos pos = (GridPos) constraints;
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.gridx = pos.getX();
			gbc.gridy = pos.getY();
			
			if (stretchToFill) {
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.fill = GridBagConstraints.BOTH;
			}
			
			super.addLayoutComponent(comp, gbc);
		} else {
			throw new IllegalArgumentException("The constraints need to be an instance of GridPos!");
		}
	}
}
