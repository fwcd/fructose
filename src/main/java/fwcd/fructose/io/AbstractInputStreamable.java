package fwcd.fructose.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import fwcd.fructose.Result;
import fwcd.fructose.function.IOFunction;

public abstract class AbstractInputStreamable implements InputStreamable {
	protected abstract InputStream openStream();
	
	@Override
	public <T> T mapStream(IOFunction<InputStream, T> mapper) {
		try (InputStream in = openStream()) {
			return mapper.apply(in);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	@Override
	public <T> Result<T, IOException> mapStreamSafely(IOFunction<InputStream, T> mapper) {
		try (InputStream in = openStream()) {
			return Result.of(mapper.apply(in));
		} catch (IOException e) {
			return Result.ofFailure(e);
		}
	}
}
