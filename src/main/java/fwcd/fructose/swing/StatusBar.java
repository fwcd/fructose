package fwcd.fructose.swing;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar implements View {
	private JPanel view;
	
	private JLabel status;
	private Thread thread;
	
	public StatusBar() {
		view = new JPanel();
		view.setLayout(new GridBagLayout());
		
		status = new JLabel();
		view.add(status);
		
		reset();
	}
	
	public synchronized void print(String message) {
		print(message, Color.WHITE);
	}
	
	public synchronized void print(String message, Color color) {
		print(message, color, 3000);
	}
	
	public synchronized void print(String message, Color color, long durationInMs) {
		if (thread != null) {
			thread.interrupt();
		}
		
		thread = new Thread(() -> {
			view.setBackground(color);
			status.setText(message);
			show();
			
			try {
				Thread.sleep(durationInMs);
			} catch (InterruptedException e) {}
			
			reset();
		});
		
		thread.setName("StatusBar Timer");
		thread.start();
	}
	
	public synchronized void display(String message) {
		display(message, Color.WHITE);
	}
	
	public synchronized void display(String message, Color color) {
		view.setBackground(color);
		status.setText(message);
		show();
	}
	
	public void reset() {
		view.setBackground(Color.WHITE);
		status.setText("");
		
		hide();
	}
	
	public void show() {
		view.setVisible(true);
	}
	
	public void hide() {
		view.setVisible(false);
	}
	
	@Override
	public JPanel getComponent() {
		return view;
	}
}
