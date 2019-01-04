package fwcd.fructose;

import static fwcd.fructose.test.TestUtils.assertThrows;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class OptionTest {
	@Test
	public void testOption() {
		Option<String> a = Option.empty();
		Option<String> b = Option.of("test");
		
		assertThat(a, not(equalTo(b)));
		assertThat("test", equalTo(b.unwrap()));
		assertThrows(NoSuchElementException.class, a::unwrap);
		assertThat(b.map(String::length), equalTo(Option.of(4)));
		assertFalse(a.map(String::length).isPresent());
		assertThat(b.flatMap(v -> Option.of(v.charAt(0))), equalTo(Option.of('t')));
		a.ifPresent(v -> fail());
		b.ifPresentOrElse(v -> assertEquals("test", v), Assert::fail);
		assertFalse(Option.ofNullable(null).isPresent());
		assertThat(a, iterableWithSize(0));
		assertThat(b, iterableWithSize(1));
		assertThat(b, contains("test"));
	}
	
	@Test
	public void testOptionInt() {
		OptionInt a = OptionInt.empty();
		OptionInt b = OptionInt.of(42);
		
		assertNotEquals(a, b);
		assertEquals(OptionInt.of(44), b.map(it -> it + 2));
		assertFalse(a.isPresent());
		assertTrue(b.isPresent());
		assertThrows(NoSuchElementException.class, a::unwrap);
		a.ifPresent(v -> fail());
		b.ifPresentOrElse(v -> assertEquals(42, v), Assert::fail);
		assertEquals(OptionInt.of(42), b.filter(v -> (v % 2) == 0));
		assertFalse(b.flatMap(v -> OptionInt.empty()).isPresent());
		assertEquals(9, a.orElse(9));
		assertEquals(42, b.orElseGet(() -> 0));
		assertThat(a, iterableWithSize(0));
		assertThat(b, iterableWithSize(1));
		assertThat(b, contains(42));
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
		assertThrows(NoSuchElementException.class, a::unwrap);
		a.ifPresent(v -> fail());
		b.ifPresentOrElse(v -> assertEquals(42, v, tolerance), Assert::fail);
		assertTrue(b.filter(v -> (v % 2) == 0).equals(OptionDouble.of(42), tolerance));
		assertFalse(b.flatMap(v -> OptionDouble.empty()).isPresent());
		assertEquals(9, a.orElse(9), tolerance);
		assertEquals(42, b.orElseGet(() -> 0), tolerance);
		assertThat(a, iterableWithSize(0));
		assertThat(b, iterableWithSize(1));
		assertThat(b.iterator().next(), closeTo(42.0, tolerance));
	}
	
	@Test
	public void testOptionLong() {
		OptionLong a = OptionLong.empty();
		OptionLong b = OptionLong.of(42);
		
		assertNotEquals(a, b);
		assertEquals(OptionLong.of(44), b.map(it -> it + 2));
		assertFalse(a.isPresent());
		assertTrue(b.isPresent());
		assertThrows(NoSuchElementException.class, a::unwrap);
		a.ifPresent(v -> fail());
		b.ifPresentOrElse(v -> assertEquals(42, v), Assert::fail);
		assertEquals(OptionLong.of(42), b.filter(v -> (v % 2) == 0));
		assertFalse(b.flatMap(v -> OptionLong.empty()).isPresent());
		assertEquals(9, a.orElse(9));
		assertEquals(42, b.orElseGet(() -> 0));
		assertThat(a, iterableWithSize(0));
		assertThat(b, iterableWithSize(1));
		assertThat(b, contains(42L));
	}
}
