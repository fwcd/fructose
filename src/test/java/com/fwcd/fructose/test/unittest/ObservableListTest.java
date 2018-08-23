package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.function.Consumer;

import com.fwcd.fructose.structs.ObservableList;

import org.junit.Test;

public class ObservableListTest {
	private int listenerCalls = 0;
	
	@Test
	public void testObservableList() {
		Consumer<List<String>> listener = str -> listenerCalls++;
		ObservableList<String> list = new ObservableList<>();
		list.listen(listener);
		list.add("three");
		list.add(0, "one");
		list.unlisten(listener);
		list.add(1, "two");
		
		
		assertEquals(2, listenerCalls);
		list.listenAndFire(str -> listenerCalls++);
		assertEquals(3, listenerCalls);
		
		assertArrayEquals(new String[] {"one", "two", "three"}, list.toArray());
		assertEquals("two", list.get(1));
		
		list.remove("three");
		list.remove(0);
		assertEquals(1, list.size());
	}
}
