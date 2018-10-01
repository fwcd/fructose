package com.fwcd.fructose;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A derived/mapped Observable that implements a
 * listener too. By combining value holder and listener,
 * a single weak reference can be used in the parent
 * observable to remove the listener when this object
 * is no longer in use.
 */
class DerivedObservable<T, I> extends ReadOnlyObservable<T> implements Consumer<I> {
	private final Function<? super I, ? extends T> mapper;
	
	public DerivedObservable(T value, Function<? super I, ? extends T> mapper) {
		super(value);
		this.mapper = mapper;
	}
	
	@Override
	public void accept(I t) {
		set(mapper.apply(t));
	}
}
