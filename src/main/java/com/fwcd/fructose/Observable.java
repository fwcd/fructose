package com.fwcd.fructose;

import java.util.function.Consumer;

/**
 * A mutable value that can be listened to.
 */
public class Observable<T> extends ReadOnlyObservable<T> implements ListenableValue<T> {
	private static final long serialVersionUID = 3258304894487752650L;
	
	public Observable(T value) { super(value); }
	
	// Publicly exposes mutating methods
	
	@Override
	public void set(T value) { super.set(value); }
	
	@Override
	public void use(Consumer<T> user) { super.use(user); }
	
	@Override
	public void fire() { super.fire(); }
}
