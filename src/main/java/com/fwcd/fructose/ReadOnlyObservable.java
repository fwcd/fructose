package com.fwcd.fructose;

import java.util.function.Consumer;

/**
 * A read-only value that can be listened to. It is not
 * immutable as the mutable subclass {@link Observable}
 * conforms to this class.
 */
public class ReadOnlyObservable<T> {
	private final EventListenerList<T> listeners = new EventListenerList<>();
	private T value;
	
	public ReadOnlyObservable(T value) {
		this.value = value;
	}
	
	public T get() {
		return value;
	}
	
	public void listen(Consumer<T> listener) {
		listeners.add(listener);
	}
	
	public void listenAndFire(Consumer<T> listener) {
		listen(listener);
		listener.accept(value);
	}
	
	public void unlisten(Consumer<T> listener) {
		listeners.remove(listener);
	}
	
	// Protected, mutating methods
	
	protected void set(T value) {
		this.value = value;
		fire();
	}
	
	protected void use(Consumer<T> user) {
		user.accept(value);
		fire();
	}
	
	protected void fire() {
		listeners.fire(value);
	}
}
