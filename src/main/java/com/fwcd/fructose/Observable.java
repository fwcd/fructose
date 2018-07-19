package com.fwcd.fructose;

import java.util.function.Consumer;

public class Observable<T> extends ReadOnlyObservable<T> {
	public Observable(T value) {
		super(value);
	}
	
	@Override
	public void set(T value) {
		super.set(value);
	}
	
	@Override
	public void use(Consumer<T> user) {
		super.use(user);
	}
	
	@Override
	public void fire() {
		super.fire();
	}
}
