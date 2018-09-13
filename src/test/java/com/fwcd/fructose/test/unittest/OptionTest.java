package com.fwcd.fructose.test.unittest;

import static com.fwcd.fructose.test.utils.TestUtils.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import com.fwcd.fructose.Option;
import com.fwcd.fructose.OptionDouble;
import com.fwcd.fructose.OptionInt;
import com.fwcd.fructose.OptionLong;

import org.junit.Assert;
import org.junit.Test;

public class OptionTest {
	@Test
	public void testOption() {
		Option<String> a = Option.empty();
		Option<String> b = Option.of("test");
		
		assertNotEquals(a, b);
		assertEquals("test", b.unwrap());
		assertThrows(a::unwrap, NoSuchElementException.class);
		assertEquals(Option.of(4), b.map(String::length));
		assertFalse(a.map(String::length).isPresent());
		assertEquals(Option.of('t'), b.flatMap(v -> Option.of(v.charAt(0))));
		a.ifPresent(v -> fail());
		b.ifPresentOrElse(v -> assertEquals("test", v), Assert::fail);
		assertFalse(Option.ofNullable(null).isPresent());
	}
	
	@Test
	public void testOptionInt() {
		OptionInt a = OptionInt.empty();
		OptionInt b = OptionInt.of(42);
		
		assertNotEquals(a, b);
		assertEquals(OptionInt.of(44), b.map(it -> it + 2));
		assertFalse(a.isPresent());
		assertTrue(b.isPresent());
		assertThrows(a::unwrap, NoSuchElementException.class);
		a.ifPresent(v -> fail());
		b.ifPresentOrElse(v -> assertEquals(42, v), Assert::fail);
		assertEquals(OptionInt.of(42), b.filter(v -> (v % 2) == 0));
		assertFalse(b.flatMap(v -> OptionInt.empty()).isPresent());
		assertEquals(9, a.orElse(9));
		assertEquals(42, b.orElseGet(() -> 0));
	}
	
	@Test
	public void testOptionDouble() {
		OptionDouble a = OptionDouble.empty();
		OptionDouble b = OptionDouble.of(42);
		double tolerance = 0.01;
		
		assertFalse(a.equals(b, tolerance));
		assertEquals(OptionDouble.of(44), b.map(it -> it + 2));
		assertFalse(a.isPresent());
		assertTrue(b.isPresent());
		assertThrows(a::unwrap, NoSuchElementException.class);
		a.ifPresent(v -> fail());
		b.ifPresentOrElse(v -> assertEquals(42, v, tolerance), Assert::fail);
		assertTrue(b.filter(v -> (v % 2) == 0).equals(OptionDouble.of(42), tolerance));
		assertFalse(b.flatMap(v -> OptionDouble.empty()).isPresent());
		assertEquals(9, a.orElse(9), tolerance);
		assertEquals(42, b.orElseGet(() -> 0), tolerance);
	}
	
	@Test
	public void testOptionLong() {
		OptionLong a = OptionLong.empty();
		OptionLong b = OptionLong.of(42);
		
		assertNotEquals(a, b);
		assertEquals(OptionLong.of(44), b.map(it -> it + 2));
		assertFalse(a.isPresent());
		assertTrue(b.isPresent());
		assertThrows(a::unwrap, NoSuchElementException.class);
		a.ifPresent(v -> fail());
		b.ifPresentOrElse(v -> assertEquals(42, v), Assert::fail);
		assertEquals(OptionLong.of(42), b.filter(v -> (v % 2) == 0));
		assertFalse(b.flatMap(v -> OptionLong.empty()).isPresent());
		assertEquals(9, a.orElse(9));
		assertEquals(42, b.orElseGet(() -> 0));
	}
}
