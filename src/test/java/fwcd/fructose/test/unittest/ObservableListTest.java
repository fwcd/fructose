package fwcd.fructose.test.unittest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import fwcd.fructose.structs.ObservableList;
import fwcd.fructose.structs.events.ListModifyEvent;

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
	
	@Test
	public void testObservableListModifyListeners() {
		ObservableList<String> original = new ObservableList<>();
		List<String> synced = new ArrayList<>();
		
		Consumer<ListModifyEvent<String>> listener = e -> e.apply(synced);
		original.listenForModifications(listener);
		
		original.add("Test");
		original.add("Demo");
		original.add(0, "Element");
		original.addAll(2, Arrays.asList("1", "2", "3"));
		original.remove("2");
		assertEquals(Arrays.asList("Element", "Test", "Demo", "1", "3"), original.get());
		assertEquals(original.get(), synced);
	}
}
