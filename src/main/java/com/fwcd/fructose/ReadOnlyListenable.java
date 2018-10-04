package com.fwcd.fructose;

import java.util.function.Consumer;

import com.fwcd.fructose.function.Subscription;

/** Anything that stores a value and can be listened to. */
public interface ReadOnlyListenable<T> {
	void listen(Consumer<T> listener);
	
	void listenAndFire(Consumer<T> listener);
	
	void unlisten(Consumer<T> listener);
	
	T get();
	
	default Subscription subscribe(Consumer<T> listener) {
		listen(listener);
		return () -> unlisten(listener);
	}
	
	default Subscription subscribeAndFire(Consumer<T> listener) {
		listenAndFire(listener);
		return () -> unlisten(listener);
	}
}
