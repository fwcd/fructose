package com.fwcd.fructose.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import com.fwcd.fructose.function.IOFunction;

public abstract class AbstractReadable implements Readable {
	protected abstract InputStream openStream();
	
	@Override
	public <T> T mapStream(IOFunction<InputStream, T> mapper) {
		try (InputStream in = openStream()) {
			return mapper.apply(in);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
