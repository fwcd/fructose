package com.fwcd.fructose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class EventListenerList<T> {
	private List<Consumer<T>> listeners = Collections.synchronizedList(new ArrayList<>());
	
	public void add(Consumer<T> listener) {
		listeners.add(listener);
	}
	
	public void remove(Consumer<T> listener) {
		listeners.remove(listener);
	}
	
	public void fire(T event) {
		synchronized (listeners) {
			for (Consumer<T> listener : listeners) {
				listener.accept(event);
			}
		}
	}
}
