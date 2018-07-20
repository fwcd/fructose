package com.fwcd.fructose.test.utils;

import static org.junit.Assert.fail;

import com.fwcd.fructose.function.ThrowingRunnable;
import com.fwcd.fructose.math.Matrix;
import com.fwcd.fructose.math.Numeric;
import com.fwcd.fructose.math.Vector;
import com.fwcd.fructose.operations.ToleranceEquatable;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

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
	
	public static <V extends ToleranceEquatable<V>> Matcher<V> approxEquals(V expected, double tolerance) {
		return new BaseMatcher<V>() {
			@Override
			@SuppressWarnings("unchecked")
			public boolean matches(Object item) {
				return ((V) item).equals(expected, tolerance);
			}
			
			@Override
			public void describeTo(Description description) {
				description
						.appendValue(expected.getClass())
						.appendText(" should equal ")
						.appendValue(expected);
			}
		};
	}
	
	public static <V extends Numeric<V>> Matcher<Vector<V>> equalsVector(Vector<V> expected, double tolerance) {
		return new BaseMatcher<Vector<V>>() {
			@Override
			@SuppressWarnings("unchecked")
			public boolean matches(Object item) {
				return ((Vector<V>) item).equals(expected, tolerance);
			}
			
			@Override
			public void describeTo(Description description) {
				description
						.appendText("Vector should equal ")
						.appendValue(expected);
			}
		};
	}
}
