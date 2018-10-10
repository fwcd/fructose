package com.fwcd.fructose;

import java.util.function.Consumer;

import com.fwcd.fructose.function.Subscription;

/** Anything that stores a value and can be listened to. */
public interface ReadOnlyListenableValue<T> extends Listenable<T> {
	void listenAndFire(Consumer<? super T> listener);
	
	T get();
	
	default Subscription subscribeAndFire(Consumer<? super T> listener) {
		listenAndFire(listener);
		return () -> unlisten(listener);
	}
}
