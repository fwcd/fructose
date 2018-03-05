package com.fwcd.fructose.structs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ArrayStack<T> implements Stack<T> {
	private Deque<T> contents = new ArrayDeque<>();
	
	public ArrayStack() {
		
	}
	
	public ArrayStack(T base) {
		push(base);
	}
	
	@Override
	public void clear() {
		contents.clear();
	}
	
	@Override
	public void push(T item) {
		contents.push(item);
	}
	
	@Override
	public void pushAll(Collection<? extends T> items) {
		for (T item : items) {
			push(item);
		}
	}
	
	/**
	 * Peeks at the top-most item on this stack.
	 * May return null when the stack is empty.
	 */
	@Override
	public T peek() {
		return contents.peek();
	}
	
	@Override
	public T pop() {
		return contents.pop();
	}
	
	@Override
	public boolean isEmpty() {
		return contents.isEmpty();
	}
	
	@Override
	public int size() {
		return contents.size();
	}
	
	public int intSum() {
		try {
			int sum = 0;
			
			for (T item : contents) {
				sum += (Integer) item;
			}
			
			return sum;
		} catch (Exception e) {
			throw new RuntimeException("Your stack type doesn't support integer addition.");
		}
	}
	
	public List<T> asList() {
		return new ArrayList<>(contents);
	}
	
	@Override
	public String toString() {
		return contents.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return contents.iterator();
	}

	@Override
	public List<T> asTopToBottomList() {
		return new ArrayList<>(contents);
	}

	@Override
	public List<T> asBottomToTopList() {
		List<T> list = asTopToBottomList();
		Collections.reverse(list);
		
		return list;
	}

	@Override
	public Optional<T> optionalPeek() {
		return Optional.ofNullable(peek());
	}

	@Override
	public void rebase(T newItem) {
		clear();
		push(newItem);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T sum() {
		try {
			Double sum = 0D;
			
			for (T item : contents) {
				sum += (Double) item;
			}
			
			return (T) sum;
		} catch (ClassCastException e) {
			throw new UnsupportedOperationException("Can't perform a sum when the items of the stack aren't numbers!");
		}
	}

	@Override
	public boolean contains(T item) {
		return contents.contains(item);
	}
}
