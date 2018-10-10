package com.fwcd.fructose;

/** Anything that stores a value and can be listened to. */
public interface ListenableValue<T> extends ReadOnlyListenableValue<T> {
	void set(T value);
}
