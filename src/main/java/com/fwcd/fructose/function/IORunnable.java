package com.fwcd.fructose.function;

import java.io.IOException;

@FunctionalInterface
public interface IORunnable {
	void run() throws IOException;
}
