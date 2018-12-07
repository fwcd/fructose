package fwcd.fructose.structs;

import java.util.Collection;
import java.util.Deque;
import java.util.List;

import fwcd.fructose.Option;

/**
 * An actual LIFO-stack that only permits stack-related operations
 * (like push and pop) as opposed to {@link java.util.Stack}
 * or {@link Deque}. Due to the add() and remove()
 * methods, it is not a {@link Collection} either.
 * 
 * @author Fredrik
 *
 * @param <T> - The item type
 */
public interface Stack<T> extends Iterable<T> {
	void clear();
	
	void push(T item);
	
	void pushAll(Collection<? extends T> items);
	
	boolean contains(T item);
	
	T pop();
	
	T peek();
	
	void rebase(T newItem);
	
	Option<T> optionalPeek();
	
	boolean isEmpty();
	
	int size();
	
	List<T> asTopToBottomList();
	
	List<T> asBottomToTopList();
	
	default T sum() {
		throw new UnsupportedOperationException("Adding the items together on a " + getClass().getSimpleName() + " is not supported!");
	}
}
