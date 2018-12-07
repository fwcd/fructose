package fwcd.fructose.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import fwcd.fructose.Result;
import fwcd.fructose.Unit;
import fwcd.fructose.function.IOConsumer;
import fwcd.fructose.function.IOFunction;

/**
 * Represents content that can be
 * read through an InputStream.
 */
public interface InputStreamable {
	/**
	 * Performs an action using the
	 * open input stream to produce a result.
	 * 
	 * <p>This method prevents resource leaks by not
	 * exposing the InputStream to other classes.</p>
	 * 
	 * @param action - The action that produces a result
	 * @return A result
	 * @throws UncheckedIOException if anything goes wrong
	 */
	<T> T mapStream(IOFunction<InputStream, T> action);
	
	<T> Result<T, IOException> mapStreamSafely(IOFunction<InputStream, T> action);
	
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
	default void withStream(IOConsumer<InputStream> action) {
		mapStream(in -> {
			action.accept(in);
			return Unit.UNIT;
		});
	}
}
