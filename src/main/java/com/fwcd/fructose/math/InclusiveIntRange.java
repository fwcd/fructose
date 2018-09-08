package com.fwcd.fructose.math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An immutable, iterable range of integers including BOTH ends.
 */
public class InclusiveIntRange implements Iterable<Integer>, Serializable {
	private static final long serialVersionUID = -1314277647900901292L;
	private final int start;
	private final int end;
	private final int step;
	private final int value;
	
	public InclusiveIntRange(int start, int end) {
		this(start, end, 1);
	}
	
	public InclusiveIntRange(int start, int end, int step) {
		this(start, end, step, start);
	}
	
	public InclusiveIntRange(int start, int end, int step, int value) {
		this.start = start;
		this.end = end;
		this.step = step;
		this.value = value;
	}
	
	public int length() {
		return (end - start) + 1;
	}
	
	public int startToValueLength() {
		return value - start;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public int getStep() {
		return step;
	}
	
	public int getValue() {
		return value;
	}
	
	public List<Integer> asList() {
		List<Integer> list = new ArrayList<>();
		
		for (int n : this) {
			list.add(n);
		}
		
		return list;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			int i = start - step;
			
			@Override
			public boolean hasNext() {
				return i <= (end - step);
			}

			@Override
			public Integer next() {
				i += step;
				
				return i;
			}
		};
	}
}
