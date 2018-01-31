package com.fwcd.fructose.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

public class DelegatePrintStream extends PrintStream {
	private final String newLine = "\n";
	private final Consumer<String> target;
	
	public DelegatePrintStream(Consumer<String> target) {
		super(new ByteArrayOutputStream(0));
		this.target = target;
	}

	@Override
	public void write(int c) {
		target.accept(String.valueOf((char) c));
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		if (len > 0 && buf.length > 0) {
			target.accept(new String(buf, off, len));
		}
	}

	@Override
	public PrintStream append(CharSequence csq) {
		target.accept(csq.toString());
		return this;
	}

	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		target.accept(csq.subSequence(start, end).toString());
		return this;
	}

	@Override
	public PrintStream append(char c) {
		target.accept(String.valueOf(c));
		return this;
	}

	@Override
	public void print(boolean b) {
		target.accept(Boolean.toString(b));
	}

	@Override
	public void print(char c) {
		target.accept(Character.toString(c));
	}

	@Override
	public void print(int i) {
		target.accept(Integer.toString(i));
	}

	@Override
	public void print(long l) {
		target.accept(Long.toString(l));
	}

	@Override
	public void print(float f) {
		target.accept(Float.toString(f));
	}

	@Override
	public void print(double d) {
		target.accept(Double.toString(d));
	}

	@Override
	public void print(char[] s) {
		target.accept(String.valueOf(s));
	}

	@Override
	public void print(String s) {
		target.accept(s);
	}

	@Override
	public void print(Object obj) {
		target.accept(obj.toString());
	}

	@Override
	public void println() {
		target.accept(newLine);
	}

	@Override
	public void println(boolean x) {
		target.accept(Boolean.toString(x) + newLine);
	}

	@Override
	public void println(char x) {
		target.accept(Character.toString(x) + newLine);
	}

	@Override
	public void println(int x) {
		target.accept(Integer.toString(x) + newLine);
	}

	@Override
	public void println(long x) {
		target.accept(Long.toString(x) + newLine);
	}

	@Override
	public void println(float x) {
		target.accept(Float.toString(x) + newLine);
	}

	@Override
	public void println(double x) {
		target.accept(Double.toString(x) + newLine);
	}

	@Override
	public void println(char[] x) {
		target.accept(String.valueOf(x) + newLine);
	}

	@Override
	public void println(String x) {
		target.accept(x + newLine);
	}

	@Override
	public void println(Object x) {
		target.accept(x.toString() + newLine);
	}
}
