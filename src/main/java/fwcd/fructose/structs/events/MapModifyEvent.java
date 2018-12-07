package fwcd.fructose.structs.events;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class MapModifyEvent<K, V> implements Serializable {
	private static final long serialVersionUID = 2539869473709687289L;
	private final Map<? extends K, ? extends V> added;
	private final Set<? extends K> removed;
	
	public MapModifyEvent(Map<? extends K, ? extends V> added, Set<? extends K> removed) {
		this.added = added;
		this.removed = removed;
	}
	
	public Map<? extends K, ? extends V> getAdded() { return added; }
	
	public Set<? extends K> getRemoved() { return removed; }
	
	public void apply(Map<K, V> map) {
		for (K key : removed) {
			map.remove(key);
		}
		for (K key : added.keySet()) {
			map.put(key, added.get(key));
		}
	}
}
