package com.fredrikw.fructose.math;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;

/**
 * An infinitely long integer sequence.
 * 
 * @author Fredrik W.
 *
 */
public class IntSequence implements Iterable<Integer> {
	private IntFunction<Integer> func;
	private IntFunction<Double> inverseFunc;
	private int minIndex;
	
	/**
	 * Creates a new integer sequence.
	 * 
	 * @param func - The function the calculates a value from a given index
	 * @param inverseFunc - The inverse function
	 * @param minIndex - The start index of this sequence
	 */
	public IntSequence(IntFunction<Integer> func, IntFunction<Double> inverseFunc, int minIndex) {
		this.func = func;
		this.inverseFunc = inverseFunc;
		this.minIndex = minIndex;
	}
	
	public IntSequence(IntFunction<Integer> func, IntFunction<Double> inverseFunc) {
		this(func, inverseFunc, 1);
	}
	
	public int get(int index) {
		if (index < minIndex) {
			throw new IndexOutOfBoundsException(index + " is out of the specified bound");
		}
		
		return func.apply(index);
	}
	
	public int getIndex(int value) {
		if (contains(value)) {
			return inverseFunc.apply(value).intValue();
		} else {
			throw new RuntimeException("Sequence does not contain value.");
		}
	}
	
	public List<Integer> asList(int maxIndex) {
		List<Integer> list = new ArrayList<>();
		
		for (int i=minIndex; i<=Math.abs(maxIndex); i++) {
			list.add(func.apply(i));
		}
		
		return list;
	}
	
	public boolean contains(int value) {
		double y = inverseFunc.apply(value);
		
		return y == (int) y && y > minIndex;
	}
	
	/**
	 * Returns an endless iterator over this integer sequence.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			
			private int i = minIndex - 1;
			
			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public Integer next() {
				i++;
				
				return func.apply(i);
			}
			
		};
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for (int i=1; i<10; i++) {
			s += Integer.toString(func.apply(minIndex + i)) + ", ";
		}
		
		return s + "...";
	}
}
