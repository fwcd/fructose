package fwcd.fructose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import fwcd.fructose.structs.WeakArrayList;

public class EventListenerList<T> implements Listenable<T> {
	private final List<Consumer<? super T>> listeners = Collections.synchronizedList(new ArrayList<>());
	private List<Consumer<? super T>> lazyWeakListeners;
	
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
	
	public void add(Consumer<? super T> listener) {
		mutateLater(() -> listeners.add(listener));
	}
	
	public void remove(Consumer<? super T> listener) {
		mutateLater(() -> listeners.remove(listener));
	}
	
	private List<Consumer<? super T>> getWeakListeners() {
		if (lazyWeakListeners == null) {
			lazyWeakListeners = Collections.synchronizedList(new WeakArrayList<>());
		}
		return lazyWeakListeners;
	}
	
	public void addWeakListener(Consumer<? super T> listener) {
		mutateLater(() -> getWeakListeners().add(listener));
	}
	
	public void removeWeakListener(Consumer<? super T> listener) {
		mutateLater(() -> getWeakListeners().remove(listener));
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
		iteratorCount++;
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
	
	@Override
	public void listen(Consumer<? super T> listener) {
		mutateLater(() -> add(listener));
	}
	
	@Override
	public void unlisten(Consumer<? super T> listener) {
		mutateLater(() -> remove(listener));
	}
}
