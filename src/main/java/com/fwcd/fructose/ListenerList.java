package com.fwcd.fructose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import com.fwcd.fructose.structs.WeakArrayList;

public class ListenerList implements Listenable<Void> {
	private List<Runnable> listeners = Collections.synchronizedList(new ArrayList<>());
	private List<Runnable> lazyWeakListeners;
	private List<Consumer<? super Void>> lazyVoidListeners;
	
	private volatile int iteratorCount = 0;
	private List<Runnable> deferredTasks = Collections.synchronizedList(new ArrayList<>());
	
	private void mutateLater(Runnable task) {
		if (iteratorCount <= 0) {
			task.run();
			iteratorCount = 0;
		} else {
			deferredTasks.add(task);
		}
	}
	
	public void add(Runnable listener) {
		mutateLater(() -> listeners.add(listener));
	}
	
	public void remove(Runnable listener) {
		mutateLater(() -> listeners.remove(listener));
	}
	
	private List<Runnable> getWeakListeners() {
		if (lazyWeakListeners == null) {
			lazyWeakListeners = Collections.synchronizedList(new WeakArrayList<>());
		}
		return lazyWeakListeners;
	}
	
	public void addWeakListener(Runnable listener) {
		mutateLater(() -> getWeakListeners().add(listener));
	}
	
	public void removeWeakListener(Runnable listener) {
		mutateLater(() -> getWeakListeners().remove(listener));
	}
	
	public boolean containsListener(Runnable listener) {
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
	
	public void fire() {
		iteratorCount++;
		synchronized (listeners) {
			for (Runnable listener : listeners) {
				listener.run();
			}
		}
		if (lazyWeakListeners != null) {
			synchronized (lazyWeakListeners) {
				for (Runnable weakListener : lazyWeakListeners) {
					weakListener.run();
				}
			}
		}
		if (lazyVoidListeners != null) {
			synchronized (lazyVoidListeners) {
				for (Consumer<? super Void> voidListener : lazyVoidListeners) {
					voidListener.accept(null);
				}
			}
		}
		iteratorCount--;
		if (iteratorCount <= 0) {
			synchronized (deferredTasks) {
				for (Runnable task : deferredTasks) {
					task.run();
				}
				deferredTasks.clear();
			}
			iteratorCount = 0;
		}
	}
	
	private List<Consumer<? super Void>> getVoidListeners() {
		if (lazyVoidListeners == null) {
			lazyVoidListeners = Collections.synchronizedList(new WeakArrayList<>());
		}
		return lazyVoidListeners;
	}
	
	@Override
	public void listen(Consumer<? super Void> listener) {
		mutateLater(() -> getVoidListeners().add(listener));
	}
	
	@Override
	public void unlisten(Consumer<? super Void> listener) {
		mutateLater(() -> getVoidListeners().remove(listener));
	}
}
