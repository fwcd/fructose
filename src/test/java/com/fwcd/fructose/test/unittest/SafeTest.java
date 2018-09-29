package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static com.fwcd.fructose.test.utils.TestUtils.assertThrows;

import com.fwcd.fructose.Result;
import com.fwcd.fructose.Safe;

import org.junit.Test;

public class SafeTest {
	@Test
	public void safeCastTest() {
		Object test = "A string";
		assertFalse(Safe.cast(test, Integer.class).isPresent());
		assertFalse(Safe.cast(null, Character.class).isPresent());
		assertEquals("A string", Safe.cast(test, String.class).orElse(null));
	}

	@Test
	public void safeAttemptTest() {
		Result<Integer, Throwable> result = Safe.attempt(() -> 2 + ((Integer) ((Object) "A string")));
		assertThrows(result::unwrap, IllegalStateException.class);
		assertTrue(result.unwrapFailure() instanceof ClassCastException);
		assertFalse(result.get().isPresent());
		assertTrue(result.getFailure().isPresent());

		Result<String, Throwable> anotherResult = Safe.attempt(() -> "Another string");
		assertThrows(anotherResult::unwrapFailure, IllegalStateException.class);
		assertEquals("Another string", anotherResult.unwrap());
		assertFalse(anotherResult.getFailure().isPresent());
		assertTrue(anotherResult.get().isPresent());
	}

	@Test
	public void safeArrayGetTest() {
		Integer[] array = {4, 5, 6};
		assertEquals(Integer.valueOf(4), Safe.arrayGet(array, 0).orElse(null));
		assertEquals(6, Safe.arrayGet(array, 2).orElse(null).intValue());
		assertFalse(Safe.arrayGet(array, -1).isPresent());
		assertFalse(Safe.arrayGet(array, 3).isPresent());
	}
}
