package com.fwcd.fructose.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InputForm implements Viewable {
	private final JPanel view;
	private HintTextField textField;
	private JButton button;
	
	private boolean allowEmptySubmit = false;
	
	public InputForm(String textFieldHint, Consumer<String> onSubmit, int width) {
		view = new JPanel();
		view.setLayout(new BorderLayout());
		
		ActionListener action = (l) -> {
			if (allowEmptySubmit || !textField.getText().isEmpty()) {
				onSubmit.accept(textField.getText());
				textField.setText("");
				textField.repaint();
			}
		};
		
		textField = new HintTextField(textFieldHint, width);
		textField.addActionListener(action);
		view.add(textField, BorderLayout.CENTER);
		
		button = new JButton("Ok");
		button.addActionListener(action);
		view.add(button, BorderLayout.EAST);
	}
	
	public void setAllowEmptySubmit(boolean allowEmptySubmit) { this.allowEmptySubmit = allowEmptySubmit; }
	
	public boolean doesAllowEmptySubmit() { return allowEmptySubmit; }
	
	public void requestFocus() { textField.requestFocus(); }
	
	@Override
	public JPanel getView() { return view; }

	public JButton getButton() { return button; }
	
	public HintTextField getTextField() { return textField; }
}
