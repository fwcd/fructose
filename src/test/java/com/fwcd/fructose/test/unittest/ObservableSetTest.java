package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.function.Consumer;

import com.fwcd.fructose.structs.ObservableSet;

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
}
