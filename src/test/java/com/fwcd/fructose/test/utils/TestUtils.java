package com.fwcd.fructose.test.utils;

import static org.junit.Assert.fail;

import com.fwcd.fructose.function.ThrowingRunnable;

public final class TestUtils {
	private TestUtils() {}

	public static void assertThrows(ThrowingRunnable<Throwable> runnable, Class<? extends Throwable> exceptionClass) {
		boolean ranWithoutException = false;
		try {
			runnable.run();
			ranWithoutException = true;
		} catch (Throwable e) {
			if (!e.getClass().equals(exceptionClass)) {
				fail("Runnable does not throw " + exceptionClass.getName() + ", but instead " + e.getClass());
			}
		}

		if (ranWithoutException) {
			fail("Runnable does not throw " + exceptionClass.getName());
		}
	}
}
