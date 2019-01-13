package fwcd.fructose.function;

import fwcd.fructose.Closer;

@FunctionalInterface
public interface Subscription extends AutoCloseable {
	void unsubscribe();
	
	default void to(Closer closer) { closer.add(this); }
	
	@Override
	default void close() { unsubscribe(); }
}
