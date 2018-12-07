package fwcd.fructose.swing.properties;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import fwcd.fructose.properties.BasicProperty;

public class TextProperty extends BasicProperty<String, JTextComponent> {
	public TextProperty() {
		set("");
	}
	
	public void bind(JLabel label) {
		label.setText(get());
		addChangeListener(() -> {
			label.setText(get());
			label.revalidate();
			label.repaint();
		});
	}
	
	@Override
	public void bind(JTextComponent textComponent) {
		textComponent.setText(get());
		textComponent.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				set(textComponent.getText());
			}
			
		});
		addChangeListener(() -> {
			textComponent.setText(get());
			textComponent.revalidate();
			textComponent.repaint();
		});
	}
}
