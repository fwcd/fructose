package com.fwcd.fructose.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A convenient wrapper for execution of
 * shell commands.
 * 
 * @author Fredrik
 *
 */
public class Shell {
	private List<String> output = new ArrayList<>();
	private Process process = null;

	public void run(String command) {
		if (!isRunning()) {
			System.out.println("[Shell " + Integer.toHexString(hashCode()) + "] " + command);
			startProcess(command);
		} else {
			System.out.println("Please wait for the current process to terminate! (or use waitFor())");
		}
	}
	
	private void startProcess(String command) {
		try {
			output.clear();
			process = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void waitFor() {
		try {
			if (isRunning()) {
				process.waitFor();
			}
		} catch (InterruptedException e) {}
	}
	
	public boolean isRunning() {
		return process != null && process.isAlive();
	}
	
	private void shellPrint(String line) {
		output.add(line);
	}
	
	private void updateOutput() {
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		try {
			while (streamReader.ready()) {
				shellPrint(streamReader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getOutput() {
		updateOutput();
		
		return output;
	}
	
	public boolean hasOutput() {
		return !getOutput().isEmpty();
	}
	
	public boolean contains(String outputString) {
		for (String outputLine : getOutput()) {
			if (outputLine.contains(outputString)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isAppPresent(String application) {
		run("which " + application);
		waitFor();
		return hasOutput();
	}

	public void terminate() {
		if (isRunning()) {
			process.destroy();
		}
	}
}
