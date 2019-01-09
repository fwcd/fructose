package fwcd.fructose;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>An object that collects closeable/disposable
 * objects and closes them when being closed.</p>
 * 
 * <p>This method is especially useful as a (transient)
 * field when dealing with subscriptions to long-lived
 * observables without leaking any observers. For
 * local variables, Java 8's {@code try-with-resources}
 * should be preferred.</p>
 */
public class Closer implements AutoCloseable, Closeable {
	private final Collection<AutoCloseable> closeables = new ArrayList<>();
	
	public Closer(AutoCloseable... initialCloseables) {
		for (AutoCloseable closeable : initialCloseables) {
			closeables.add(closeable);
		}
	}
	
	public void add(AutoCloseable closeable) {
		closeables.add(closeable);
	}
	
	public void addAll(AutoCloseable... added) {
		for (AutoCloseable closeable : added) {
			add(closeable);
		}
	}
	
	public void addAll(Iterable<? extends AutoCloseable> added) {
		for (AutoCloseable closeable : added) {
			add(closeable);
		}
	}
	
	@Override
	public void close() {
		try {
			for (AutoCloseable closeable : closeables) {
				closeable.close();
			}
		} catch (Exception e) {
			throw new CloserException(e);
		}
	}
	
	public static class CloserException extends RuntimeException {
		private static final long serialVersionUID = 1915372897358108915L;
		
		public CloserException(Throwable cause) {
			super(cause);
		}
	}
}
