package com.fredrikw.fructose.structs;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 * A set using a custom equality checker.
 * 
 * @author Fredrik
 *
 * @param <T> - The item type
 */
public class CustomSet<T> extends AbstractSet<T> {
	private final Set<T> contents = new HashSet<>();
	private final BiPredicate<? super T, ? super T> equalityChecker;

	public CustomSet(BiPredicate<? super T, ? super T> equalityChecker) {
		this.equalityChecker = equalityChecker;
	}
	
	@Override
	public Iterator<T> iterator() {
		return contents.iterator();
	}

	@Override
	public int size() {
		return contents.size();
	}

	/**
	 * Checks if this set contains the given
	 * object. <b>Expect linear time performance.</b>
	 * 
	 * @param o - The searched object
	 * @return Whether this set contains the object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		try {
			for (T item : contents) {
				if (equalityChecker.test(item, (T) o)) {
					return true;
				}
			}
			
			return false;
		} catch (ClassCastException e) {
			return false;
		}
	}

	@Override
	public boolean add(T e) {
		return contents.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return contents.remove(o);
	}
}
