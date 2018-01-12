package com.fredrikw.fructose.structs;

import java.util.Optional;
import java.util.function.BiPredicate;

public class ArraySetStack<T> extends ArrayStack<T> implements SetStack<T> {
	private Optional<BiPredicate<? super T, ? super T>> equalityChecker;
	
	public ArraySetStack() {
		equalityChecker = Optional.empty();
	}
	
	public ArraySetStack(BiPredicate<? super T, ? super T> equalityChecker) {
		this.equalityChecker = Optional.of(equalityChecker);
	}
	
	@Override
	public boolean contains(T searchedItem) {
		if (equalityChecker.isPresent()) {
			for (T item : this) {
				if (equalityChecker.orElse(null).test(item, searchedItem)) {
					return true;
				}
			}
			
			return false;
		} else {
			return super.contains(searchedItem);
		}
	}
	
	@Override
	public void push(T item) {
		if (!contains(item)) {
			super.push(item);
		}
	}
}
