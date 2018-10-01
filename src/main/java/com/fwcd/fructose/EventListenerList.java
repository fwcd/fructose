package com.fwcd.fructose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import com.fwcd.fructose.structs.WeakArrayList;

public class EventListenerList<T> {
	private final List<Consumer<T>> listeners = Collections.synchronizedList(new ArrayList<>());
	private List<Consumer<T>> lazyWeakListeners;
	
	public void add(Consumer<T> listener) {
		listeners.add(listener);
	}
	
	public void remove(Consumer<T> listener) {
		listeners.remove(listener);
	}
	
	private List<Consumer<T>> getWeakListeners() {
		if (lazyWeakListeners == null) {
			lazyWeakListeners = Collections.synchronizedList(new WeakArrayList<>());
		}
		return lazyWeakListeners;
	}
	
	public void addWeakListener(Consumer<T> listener) {
		getWeakListeners().add(listener);
	}
	
	public void removeWeakListener(Consumer<T> listener) {
		getWeakListeners().remove(listener);
	}
	
	public boolean containsListener(Consumer<T> listener) {
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
	
	public int listenerCount() { return listeners.size(); }
	
	public int weakListenerCount() { return (lazyWeakListeners == null) ? 0 : lazyWeakListeners.size(); } 
	
	public int size() { return listenerCount() + weakListenerCount(); }
	
	public void fire(T event) {
		synchronized (listeners) {
			for (Consumer<T> listener : listeners) {
				listener.accept(event);
			}
			if (lazyWeakListeners != null) {
				for (Consumer<T> weakListener : lazyWeakListeners) {
					weakListener.accept(event);
				}
			}
		}
	}
}
