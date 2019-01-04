package fwcd.fructose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LazyTest {
	@Test
	public void testLazy() {
		Lazy<String> a = Lazy.of(() -> "A string");
		assertFalse(a.hasBeenInitialized());
		assertEquals("A string", a.get());
		assertTrue(a.hasBeenInitialized());

		Lazy<Integer> b = Lazy.of(() -> 2 + 3);
		Lazy<Integer> c = b.map(v -> v * 2);
		assertFalse(b.hasBeenInitialized());
		assertEquals(Integer.valueOf(5), b.get());
		assertFalse(c.hasBeenInitialized());
		assertEquals(10, c.get().intValue());
		assertTrue(b.hasBeenInitialized());
		assertTrue(c.hasBeenInitialized());
		
		Flag evaluated = new Flag();
		Lazy<Integer> d = Lazy.of(() -> {
			evaluated.value = true;
			return 3 * 3;
		});
		assertEquals(evaluated.value, d.hasBeenInitialized());
		
		Lazy<String> e = d.map(it -> it.toString());
		assertFalse(e.hasBeenInitialized());
		assertEquals("9", e.get());
		assertTrue(d.hasBeenInitialized());
		assertTrue(evaluated.value);
	}
	
	private static class Flag {
		boolean value = false;
	}
}
