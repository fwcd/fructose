package com.fwcd.fructose;

import java.util.function.Consumer;

public class ReadOnlyObservable<T> {
	private final EventListenerList<T> listeners = new EventListenerList<>();
	private T value;
	
	public ReadOnlyObservable(T value) {
		this.value = value;
	}
	
	public T get() {
		return value;
	}
	
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
	
	public void listen(Consumer<T> listener) {
		listeners.add(listener);
	}
}
