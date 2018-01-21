package com.fredrikw.fructose.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.PrintStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import com.fredrikw.fructose.shell.Shell;

public class ShellWindow {
	private ConsolePane console;

	public ShellWindow(Shell shell) {
		this(shell, false);
	}

	public ShellWindow(Shell shell, boolean persistent) {
		this(shell, persistent, null);
	}
	
	public ShellWindow(Shell shell, boolean persistent, Runnable onClose) {
		JFrame shellWindow = new JFrame("Shell Output (" + Integer.toHexString(hashCode()) + ")");
		shellWindow.setMinimumSize(new Dimension(600, 250));
		shellWindow.setLayout(new BorderLayout());
		
		console = new ConsolePane();
		shellWindow.add(console.getView(), BorderLayout.CENTER);
		
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(() -> {
			if (shell.isRunning()) {
				console.clear();
				console.printlns(shell.getOutput());
			} else {
				exec.shutdown();
				
				if (!persistent) {
					if (onClose != null) {
						onClose.run();
					}
					
					shellWindow.dispose();
				}
			}
		}, 1000, 100, TimeUnit.MILLISECONDS);
		
		shellWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		shellWindow.setVisible(true);
	}

	public PrintStream getOutStream() {
		return console.getOutStream();
	}
	
	public PrintStream getErrStream() {
		return console.getErrStream();
	}
}