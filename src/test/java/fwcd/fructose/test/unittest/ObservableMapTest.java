package fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import fwcd.fructose.structs.ObservableMap;
import fwcd.fructose.structs.events.MapModifyEvent;

import org.junit.Test;

public class ObservableMapTest {
	private int listenerCalls = 0;
	
	@Test
	public void testObservableMap() {
		Consumer<Map<String, String>> listener = str -> listenerCalls++;
		ObservableMap<String, String> map = new ObservableMap<>();
		map.listen(listener);
		map.put("one", "one");
		map.put("two", "four");
		map.unlisten(listener);
		map.put("three", "nine");
		
		assertEquals(2, listenerCalls);
		map.listenAndFire(str -> listenerCalls++);
		assertEquals(3, listenerCalls);
		
		assertEquals("four", map.get("two"));
		
		map.put("four", "sixteen");
		assertEquals(4, map.size());
	}
	
	@Test
	public void testObservableMapModifyListeners() {
		ObservableMap<String, String> original = new ObservableMap<>();
		Map<String, String> synced = new HashMap<>();
		
		Consumer<MapModifyEvent<String, String>> listener = e -> e.apply(synced);
		original.listenForModifications(listener);
		
		original.put("one", "one");
		original.put("two", "four");
		original.put("three", "nine");
		original.remove("two");
		assertEquals(original.get(), synced);
		
		original.clear();
		assertEquals(original.get(), synced);
	}
}
