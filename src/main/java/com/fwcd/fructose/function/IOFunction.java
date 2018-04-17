package com.fwcd.fructose.function;

import java.io.IOException;

@FunctionalInterface
public interface IOFunction<I, O> {
	O apply(I input) throws IOException;
}
