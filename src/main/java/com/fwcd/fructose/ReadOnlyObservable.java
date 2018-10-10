package com.fwcd.fructose;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A read-only value that can be listened to. It is not
 * immutable as the mutable subclass {@link Observable}
 * conforms to this class.
 */
public class ReadOnlyObservable<T> implements ReadOnlyListenableValue<T> {
	private final EventListenerList<T> listeners = new EventListenerList<>();
	private T value;
	
	public ReadOnlyObservable(T value) {
		this.value = value;
	}
	
	@Override
	public T get() {
		return value;
	}
	
	@Override
	public void listen(Consumer<? super T> listener) {
		listeners.add(listener);
	}
	
	@Override
	public void listenAndFire(Consumer<? super T> listener) {
		listen(listener);
		listener.accept(value);
	}
	
	@Override
	public void unlisten(Consumer<? super T> listener) {
		listeners.remove(listener);
	}
	
	public void listenWeakly(Consumer<? super T> listener) {
		listeners.addWeakListener(listener);
	}
	
	public void listenWeaklyAndFire(Consumer<? super T> listener) {
		listenWeakly(listener);
		listener.accept(value);
	}
	
	public void unlistenWeakly(Consumer<? super T> listener) {
		listeners.removeWeakListener(listener);
	}
	
	public int strongListenerCount() { return listeners.strongListenerCount(); }
	
	public int weakListenerCount() { return listeners.weakListenerCount(); }
	
	public int listenerCount() { return listeners.size(); }
	
	/**
	 * @deprecated Use {@code mapStrongly} or {@code mapWeakly}
	 */
	@Deprecated
	public <R> ReadOnlyObservable<R> map(Function<? super T, ? extends R> mapper) {
		return mapStrongly(mapper);
	}
	
	public <R> ReadOnlyObservable<R> mapStrongly(Function<? super T, ? extends R> mapper) {
		DerivedObservable<R, T> result = new DerivedObservable<>(mapper.apply(value), mapper);
		listen(result);
		return result;
	}
	
	public <R> ReadOnlyObservable<R> mapWeakly(Function<? super T, ? extends R> mapper) {
		DerivedObservable<R, T> result = new DerivedObservable<>(mapper.apply(value), mapper);
		listenWeakly(result);
		return result;
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
