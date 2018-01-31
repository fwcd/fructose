package com.fwcd.fructose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Permutations<T> implements Iterable<List<T>> {
	private Set<List<T>> permutations;
	
	@SafeVarargs
	public Permutations(T... args) {
		this(Arrays.asList(args));
	}
	
	public Permutations(List<T> list) {
		permutations = new HashSet<>();
		permute(list, 0);
	}
	
	public Set<List<T>> get() {
		return permutations;
	}
	
	public int amount() {
		return permutations.size();
	}
	
	public List<T> pickRandom() {
		Random random = ThreadLocalRandom.current();
		return permutations.stream()
				.skip((long) (permutations.size() * random.nextDouble()))
				.findFirst()
				.orElseThrow(NoSuchElementException::new);
	}
	
	private void permute(List<T> list, int baseElement) {
		for (int i=baseElement; i<list.size(); i++) {
			Collections.swap(list, baseElement, i);
			permute(list, baseElement + 1);
			Collections.swap(list, i, baseElement);
		}
		
		if (baseElement == list.size() - 1) {
			permutations.add(new ArrayList<>(list));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getAsNums() {
		try {
			List<Integer> permutationNums = new ArrayList<>();
			
			for (List<T> permutation : permutations) {
				permutationNums.add(ListUtils.toInt((List<Integer>) permutation));
			}
			
			return permutationNums;
		} catch (ClassCastException e) {
			throw new RuntimeException("Permutations type has to be integer.");
		}
	}
	
	public List<String> getAsStrings() {
		List<String> permutationStrings = new ArrayList<>();
		
		for (List<T> permutation : permutations) {
			String s = "";
			
			for (T item : permutation) {
				s += item.toString();
			}
			
			permutationStrings.add(s);
		}
		
		return permutationStrings;
	}
	
	@Override
	public String toString() {
		return permutations.toString();
	}

	@Override
	public Iterator<List<T>> iterator() {
		return permutations.iterator();
	}
}
