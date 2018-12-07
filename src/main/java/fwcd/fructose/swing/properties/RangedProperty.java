package fwcd.fructose.swing.properties;

import javax.swing.JLabel;

import fwcd.fructose.math.InclusiveIntRange;
import fwcd.fructose.properties.BasicProperty;
import fwcd.fructose.swing.RangedSlider;

public class RangedProperty extends BasicProperty<InclusiveIntRange, RangedSlider> {
	public RangedProperty(InclusiveIntRange startRange) {
		set(startRange);
	}

	private String getObserverText() {
		return Integer.toString(get().getValue()) + "/" + Integer.toString(get().length() + 1);
	}

	@Override
	public void bind(RangedSlider component) {
		component.setRange(get());
		component.addChangeListener((l) -> {
			set(component.getRange());
		});
		addChangeListener(() -> component.setRange(get()));
	}
	
	public void bindObserver(JLabel label) {
		label.setText(getObserverText());
		addChangeListener(() -> label.setText(getObserverText()));
	}

	public int getValue() {
		return get().getValue();
	}
}
