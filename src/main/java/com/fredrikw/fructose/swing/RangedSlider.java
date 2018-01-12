package com.fredrikw.fructose.swing;

import javax.swing.JSlider;

import com.fredrikw.fructose.math.InclusiveIntRange;

public class RangedSlider extends JSlider {
	private static final long serialVersionUID = 1L;
	
	public RangedSlider() {
		setRange(new InclusiveIntRange(0, 0));
	}
	
	public RangedSlider(InclusiveIntRange range) {
		setRange(range);
	}
	
	public void setRange(InclusiveIntRange range) {
		setMinimum(range.getStart());
		setMaximum(range.getEnd());
		setValue(range.getValue());
	}

	public InclusiveIntRange getRange() {
		return new InclusiveIntRange(getMinimum(), getMaximum(), 1, getValue());
	}
}
