package fwcd.fructose.structs;

import java.util.function.BiPredicate;

import fwcd.fructose.Option;

public class ArraySetStack<T> extends ArrayStack<T> implements SetStack<T> {
	private Option<BiPredicate<? super T, ? super T>> equalityChecker;
	
	public ArraySetStack() {
		equalityChecker = Option.empty();
	}
	
	public ArraySetStack(BiPredicate<? super T, ? super T> equalityChecker) {
		this.equalityChecker = Option.of(equalityChecker);
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
