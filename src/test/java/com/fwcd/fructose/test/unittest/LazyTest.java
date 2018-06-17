package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fwcd.fructose.Lazy;

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
	}
}
