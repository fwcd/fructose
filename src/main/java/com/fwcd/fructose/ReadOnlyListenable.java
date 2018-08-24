package com.fwcd.fructose;

import java.util.function.Consumer;

/** Anything that stores a value and can be listened to. */
public interface ReadOnlyListenable<T> {
	void listen(Consumer<T> listener);
	
	void listenAndFire(Consumer<T> listener);
	
	void unlisten(Consumer<T> listener);
	
	T get();
}
