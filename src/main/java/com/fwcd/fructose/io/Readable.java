package com.fwcd.fructose.io;

import java.io.InputStream;
import java.io.UncheckedIOException;

import com.fwcd.fructose.function.IOConsumer;
import com.fwcd.fructose.function.IOFunction;

/**
 * Represents content that can be
 * read out through an InputStream.
 * 
 * @author Fredrik
 *
 */
public interface Readable {
	/**
	 * <p>Performs an action using the
	 * open input stream to produce a result.</p>
	 * 
	 * <p>This method inherently prevents resource leaks by not
	 * exposing the InputStream to other classes.</p>
	 * 
	 * @param action - The action that produces a result
	 * @return A result
	 * @throws UncheckedIOException if anything goes wrong
	 */
	<T> T mapStream(IOFunction<InputStream, T> action);
	
	/**
	 * <p>Performs an action/a procedure using the
	 * open input stream.</p>
	 * 
	 * <p>This method inherently prevents resource leaks by not
	 * exposing the InputStream to other classes.</p>
	 * 
	 * @param action - The action/procedure
	 * @throws UncheckedIOException if anything goes wrong
	 */
	default <T> void withStream(IOConsumer<InputStream> action) {
		mapStream(in -> {
			action.accept(in);
			return (Void) null;
		});
	}
}
