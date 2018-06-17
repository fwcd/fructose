package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static com.fwcd.fructose.test.utils.TestUtils.assertThrows;

import java.util.NoSuchElementException;

import com.fwcd.fructose.Either;

import org.junit.Test;

public class EitherTest {
	@Test
	public void testEither() {
		Either<String, Integer> a = Either.ofLeft("This is a string");
		assertEquals("This is a string", a.expectLeft());
		assertEquals("This is a string", a.getLeft().orElseThrow(NoSuchElementException::new));
		assertTrue(a.getLeft().isPresent());
		assertTrue(!a.getRight().isPresent());
		assertThrows(a::expectRight, NoSuchElementException.class);

		a = Either.ofRight(24);
		assertEquals(24, a.get());
		assertEquals(Integer.valueOf(24), a.expectRight());
		assertThrows(a::expectLeft, NoSuchElementException.class);
	}
}
