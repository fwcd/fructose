package fwcd.fructose.structs.events;

import java.io.Serializable;
import java.util.Set;

public class SetModifyEvent<T> implements Serializable {
	private static final long serialVersionUID = 9090357199159279065L;
	private final Set<? extends T> added;
	private final Set<? extends T> removed;
	
	public SetModifyEvent(Set<? extends T> added, Set<? extends T> removed) {
		this.added = added;
		this.removed = removed;
	}
	
	public Set<? extends T> getAdded() { return added; }
	
	public Set<? extends T> getRemoved() { return removed; }
	
	public void apply(Set<T> set) {
		set.removeAll(removed);
		set.addAll(added);
	}
}
