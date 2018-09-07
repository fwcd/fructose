package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fwcd.fructose.Pair;
import com.fwcd.fructose.math.IntRange;
import com.fwcd.fructose.structs.events.ListModifyEvent;
import com.fwcd.fructose.structs.events.MapModifyEvent;
import com.fwcd.fructose.structs.events.SetModifyEvent;

import org.junit.Test;

public class CollectionModifyEventTest {
	@Test
	public void testListModifyEvent() {
		List<Integer> a = list(3, 5, 3);
		new ListModifyEvent<>(list(4), range(3, 3)).apply(a);
		assertEquals(list(3, 5, 3, 4), a);
		
		List<Integer> b = list(9, 3, 2, 1);
		new ListModifyEvent<Integer>(list(), range(1, 3)).apply(b);
		assertEquals(list(9, 1), b);
	}
	
	@Test
	public void testSetModifyEvent() {
		Set<String> a = set("word", "sentence", "letter");
		new SetModifyEvent<>(set("apple", "orange"), set("word")).apply(a);
		assertEquals(set("apple", "orange", "sentence", "letter"), a);
	}
	
	@Test
	public void testMapModifyEvent() {
		Map<String, String> a = map(
			pair("one", "one"),
			pair("two", "four"),
			pair("three", "six")
		);
		new MapModifyEvent<>(map(pair("four", "eight")), set("two")).apply(a);
		assertEquals(map(pair("one", "one"), pair("three", "six"), pair("four", "eight")), a);
	}
	
	// Convenience methods
	
	private IntRange range(int start, int end) {
		return new IntRange(start, end);
	}
	
	@SafeVarargs
	private final <T> List<T> list(T... elements) {
		return Arrays.stream(elements).collect(Collectors.toCollection(ArrayList::new));
	}
	
	@SafeVarargs
	private final <T> Set<T> set(T... elements) {
		return Arrays.stream(elements).collect(Collectors.toCollection(HashSet::new));
	}
	
	@SafeVarargs
	private final <K, V> Map<K, V> map(Pair<K, V>... pairs) {
		Map<K, V> map = new HashMap<>();
		for (Pair<K, V> pair : pairs) {
			map.put(pair.getLeft(), pair.getRight());
		}
		return map;
	}
	
	private final <K, V> Pair<K, V> pair(K key, V value) {
		return new Pair<>(key, value);
	}
}
