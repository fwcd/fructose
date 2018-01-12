package com.fredrikw.fructose.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.fredrikw.fructose.shell.Shell;

public class ShellWindow {
	private JTextArea outputArea;
	private JScrollPane scrollPane;

	public ShellWindow(Shell shell, boolean persistent, Runnable onClose) {
		JFrame shellWindow = new JFrame("Shell Output (" + Integer.toHexString(hashCode()) + ")");
		shellWindow.setMinimumSize(new Dimension(600, 250));
		shellWindow.setLayout(new BorderLayout());
		
		outputArea = new JTextArea();
		outputArea.setBackground(Color.BLACK);
		outputArea.setForeground(Color.WHITE);
		
		scrollPane = new JScrollPane(outputArea);
		shellWindow.add(scrollPane);
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (shell.isRunning()) {
					outputArea.setText("");
					
					for (String line : shell.getOutput()) {
						outputArea.setText(outputArea.getText() + "\n" + line);
					}
					
					JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
					vScrollBar.setValue(vScrollBar.getMaximum());
					
					shellWindow.revalidate();
					shellWindow.repaint();
				} else {
					timer.cancel();
					
					if (onClose != null) {
						onClose.run();
					}
					
					if (!persistent) {
						shellWindow.dispose();
					}
				}
			}
			
		}, 1000, 100);
		
		shellWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		shellWindow.setVisible(true);
	}
}