package fwcd.fructose;

import java.util.function.Consumer;

import fwcd.fructose.function.Subscription;

/** Anything that can be listened to. */
public interface Listenable<T> {
	void listen(Consumer<? super T> listener);
	
	void unlisten(Consumer<? super T> listener);
	
	default Subscription subscribe(Consumer<? super T> listener) {
		listen(listener);
		return () -> unlisten(listener);
	}
}
