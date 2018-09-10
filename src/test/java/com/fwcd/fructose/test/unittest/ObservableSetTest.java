package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fwcd.fructose.structs.ObservableSet;
import com.fwcd.fructose.structs.events.SetModifyEvent;

import org.junit.Test;

public class ObservableSetTest {
	private int listenerCalls = 0;
	
	@Test
	public void testObservableSet() {
		Consumer<Set<String>> listener = str -> listenerCalls++;
		ObservableSet<String> set = new ObservableSet<>();
		set.listen(listener);
		set.add("one");
		set.add("two");
		set.unlisten(listener);
		set.add("three");
		
		assertEquals(2, listenerCalls);
		set.listenAndFire(str -> listenerCalls++);
		assertEquals(3, listenerCalls);
		
		assertArrayEquals(new String[] {"one", "two", "three"}, set.toArray());
		
		set.add("three");
		assertEquals(3, set.size());
	}
	
	@Test
	public void testObservableSetModifyListeners() {
		ObservableSet<String> original = new ObservableSet<>();
		Set<String> synced = new HashSet<>();
		
		Consumer<SetModifyEvent<String>> listener = e -> e.apply(synced);
		original.listenForModifications(listener);
		
		original.add("Test");
		original.add("Demo");
		original.addAll(Arrays.asList("Element", "1", "2", "3"));
		original.remove("2");
		assertEquals(Stream.of("Element", "Test", "Demo", "1", "3").collect(Collectors.toSet()), original.get());
		assertEquals(original.get(), synced);
	}
}
