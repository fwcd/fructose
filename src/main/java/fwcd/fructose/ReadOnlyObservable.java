package fwcd.fructose;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A read-only value that can be listened to. It is not
 * immutable as the mutable subclass {@link Observable}
 * conforms to this class.
 */
public class ReadOnlyObservable<T> implements Serializable, ReadOnlyListenableValue<T> {
	private static final long serialVersionUID = 2342657524406442805L;
	private transient EventListenerList<T> nullableListeners;
	private T value;
	
	public ReadOnlyObservable(T value) {
		this.value = value;
	}
	
	@Override
	public T get() {
		return value;
	}
	
	private EventListenerList<T> getListeners() {
		if (nullableListeners == null) {
			nullableListeners = new EventListenerList<>();
		}
		return nullableListeners;
	}
	
	@Override
	public void listen(Consumer<? super T> listener) {
		getListeners().add(listener);
	}
	
	@Override
	public void listenAndFire(Consumer<? super T> listener) {
		listen(listener);
		listener.accept(value);
	}
	
	@Override
	public void unlisten(Consumer<? super T> listener) {
		getListeners().remove(listener);
	}
	
	public void listenWeakly(Consumer<? super T> listener) {
		getListeners().addWeakListener(listener);
	}
	
	public void listenWeaklyAndFire(Consumer<? super T> listener) {
		listenWeakly(listener);
		listener.accept(value);
	}
	
	public void unlistenWeakly(Consumer<? super T> listener) {
		getListeners().removeWeakListener(listener);
	}
	
	public int strongListenerCount() { return getListeners().strongListenerCount(); }
	
	public int weakListenerCount() { return getListeners().weakListenerCount(); }
	
	public int listenerCount() { return getListeners().size(); }
	
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
		getListeners().fire(value);
	}
}
