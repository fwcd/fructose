package com.fwcd.fructose.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.PrintStream;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.fwcd.fructose.Lazy;
import com.fwcd.fructose.exception.Rethrow;
import com.fwcd.fructose.io.DelegatePrintStream;

public class ConsolePane implements Viewable {
	private final JScrollPane view;
	private final JTextPane textArea;
	
	private final Lazy<PrintStream> errStream = new Lazy<>(this::createErrStream);
	private final Lazy<PrintStream> outStream = new Lazy<>(this::createOutStream);
	private boolean autoscroll = true;

	public ConsolePane() {
		this(true);
	}
	
	public ConsolePane(boolean lineWrap) {
		textArea = new JTextPane();
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		
		if (lineWrap) {
			view = new JScrollPane(textArea);
		} else {
			JPanel wrapper = new JPanel();
			wrapper.setLayout(new BorderLayout());
			wrapper.add(textArea, BorderLayout.CENTER);
			view = new JScrollPane(wrapper);
		}
	}
	
	public void setAutoscrolling(boolean autoscroll) {
		this.autoscroll  = autoscroll;
	}
	
	public void setForeground(Color color) {
		view.setForeground(color);
	}
	
	public void setBackground(Color color) {
		view.setBackground(color);
	}
	
	private PrintStream createOutStream() {
		return new PrintStream(new DelegatePrintStream(this::print));
	}
	
	private PrintStream createErrStream() {
		return new PrintStream(new DelegatePrintStream(s -> print(s, Color.RED)));
	}
	
	public PrintStream getOutStream() {
		return outStream.get();
	}
	
	public PrintStream getErrStream() {
		return errStream.get();
	}
	
	public void clear() {
		textArea.setText("");
	}
	
	public void printStackTrace(Throwable t) {
		t.printStackTrace(getErrStream());
	}
	
	public void printlns(Iterable<String> lines) {
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			builder.append(line).append("\n");
		}
		print(builder.toString());
	}
	
	public void printlns(String... lines) {
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			builder.append(line).append("\n");
		}
		print(builder.toString());
	}
	
	public void println(String line) {
		print(line + "\n");
	}
	
	public void println(String line, Color color) {
		print(line + "\n", color);
	}
	
	public void print(String s) {
		write(s, null);
		updateView();
	}
	
	public void print(String s, Color color) {
		MutableAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setForeground(attributes, color);
		write(s, attributes);
		updateView();
	}

	private void write(String s, AttributeSet attributes) {
		StyledDocument doc = textArea.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), s, attributes);
		} catch (BadLocationException e) {
			throw new Rethrow(e);
		}
	}

	private void updateView() {
		if (autoscroll) {
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		SwingUtilities.invokeLater(view::repaint);
	}

	@Override
	public JComponent getView() {
		return view;
	}
}
