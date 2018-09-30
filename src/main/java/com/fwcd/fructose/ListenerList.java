package com.fwcd.fructose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fwcd.fructose.structs.WeakArrayList;

public class ListenerList {
	private List<Runnable> listeners = Collections.synchronizedList(new ArrayList<>());
	private List<Runnable> lazyWeakListeners;
	
	public void add(Runnable listener) {
		listeners.add(listener);
	}
	
	public void remove(Runnable listener) {
		listeners.remove(listener);
	}
	
	private List<Runnable> getWeakListeners() {
		if (lazyWeakListeners == null) {
			lazyWeakListeners = Collections.synchronizedList(new WeakArrayList<>());
		}
		return lazyWeakListeners;
	}
	
	public void addWeakListener(Runnable listener) {
		getWeakListeners().add(listener);
	}
	
	public void removeWeakListener(Runnable listener) {
		getWeakListeners().remove(listener);
	}
	
	public void fire() {
		synchronized (listeners) {
			for (Runnable listener : listeners) {
				listener.run();
			}
			if (lazyWeakListeners != null) {
				for (Runnable weakListener : lazyWeakListeners) {
					weakListener.run();
				}
			}
		}
	}
}
