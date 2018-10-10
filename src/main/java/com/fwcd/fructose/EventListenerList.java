package com.fwcd.fructose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import com.fwcd.fructose.structs.WeakArrayList;

public class EventListenerList<T> implements Listenable<T> {
	private final List<Consumer<? super T>> listeners = Collections.synchronizedList(new ArrayList<>());
	private List<Consumer<? super T>> lazyWeakListeners;
	
	public void add(Consumer<? super T> listener) {
		listeners.add(listener);
	}
	
	public void remove(Consumer<? super T> listener) {
		listeners.remove(listener);
	}
	
	private List<Consumer<? super T>> getWeakListeners() {
		if (lazyWeakListeners == null) {
			lazyWeakListeners = Collections.synchronizedList(new WeakArrayList<>());
		}
		return lazyWeakListeners;
	}
	
	public void addWeakListener(Consumer<? super T> listener) {
		getWeakListeners().add(listener);
	}
	
	public void removeWeakListener(Consumer<? super T> listener) {
		getWeakListeners().remove(listener);
	}
	
	public boolean containsListener(Consumer<? super T> listener) {
		if (listeners.contains(listener)) {
			return true;
		}
		if (lazyWeakListeners != null) {
			if (lazyWeakListeners.contains(listener)) {
				return true;
			}
		}
		return false;
	}
	
	public int strongListenerCount() { return listeners.size(); }
	
	public int weakListenerCount() { return (lazyWeakListeners == null) ? 0 : lazyWeakListeners.size(); } 
	
	public int size() { return strongListenerCount() + weakListenerCount(); }
	
	public void fire(T event) {
		synchronized (listeners) {
			for (Consumer<? super T> listener : listeners) {
				listener.accept(event);
			}
		}
		if (lazyWeakListeners != null) {
			synchronized (lazyWeakListeners) {
				for (Consumer<? super T> weakListener : lazyWeakListeners) {
					weakListener.accept(event);
				}
			}
		}
	}
	
	@Override
	public void listen(Consumer<? super T> listener) { add(listener); }
	
	@Override
	public void unlisten(Consumer<? super T> listener) { remove(listener); }
}
