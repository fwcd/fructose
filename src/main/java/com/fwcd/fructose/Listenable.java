package com.fwcd.fructose;

/** Anything that stores a value and can be listened to. */
public interface Listenable<T> extends ReadOnlyListenable<T> {
	void set(T value);
}
