package fwcd.fructose.properties;

import fwcd.fructose.ListenerList;

/**
 * A basic property implementation.
 * 
 * @author Fredrik
 *
 * @param <T> - The data type
 * @param <B> - The binding type
 */
public abstract class BasicProperty<T, B> implements Property<T, B> {
	private transient ListenerList changeListeners = new ListenerList();
	private T data;
	
	@Override
	public void set(T data) {
		this.data = data;
		announceChange();
	}

	@Override
	public T get() {
		return data;
	}
	
	private ListenerList getChangeListeners() {
		if (changeListeners == null) {
			changeListeners = new ListenerList();
		}
		
		return changeListeners;
	}
	
	protected void announceChange() {
		getChangeListeners().fire();
	}
	
	public void addChangeListener(Runnable changeListener) {
		getChangeListeners().add(changeListener);
	}
	
	public void removeChangeListener(Runnable changeListener) {
		getChangeListeners().remove(changeListener);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}
