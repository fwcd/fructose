package com.fwcd.fructose.structs.events;

import java.io.Serializable;
import java.util.List;

import com.fwcd.fructose.math.IntRange;

public class ListModifyEvent<T> implements Serializable {
	private static final long serialVersionUID = -1122186771573362774L;
	private final List<? extends T> elements;
	private final IntRange indices;
	
	public ListModifyEvent(List<? extends T> elements, IntRange indices) {
		this.elements = elements;
		this.indices = indices;
	}
	
	public List<? extends T> getElements() { return elements; }
	
	public IntRange getIndices() { return indices; }
	
	public void apply(List<T> list) {
		List<T> segment = list.subList(indices.getStart(), indices.getEnd());
		segment.clear();
		segment.addAll(elements);
	}
}
