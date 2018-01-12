package com.fredrikw.fructose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListenerList {
	private List<Runnable> listeners = Collections.synchronizedList(new ArrayList<>());
	
	public void add(Runnable listener) {
		listeners.add(listener);
	}
	
	public void remove(Runnable listener) {
		listeners.remove(listener);
	}
	
	public void fire() {
		synchronized (listeners) {
			for (Runnable listener : listeners) {
				listener.run();
			}
		}
	}
}
