package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fwcd.fructose.When;

import org.junit.Test;

public class WhenTest {
	private boolean itWorks;

	@Test
	public void testWhen() {
		itWorks = false;
		When.it()
			.isTrue(true, () -> itWorks = true)
			.isTrue(false, () -> fail("When isTrue(false, ...) should never run"))
			.elseDo(() -> itWorks = false);
		assertTrue(itWorks);

		itWorks = false;
		When.it()
			.isTrue(false, () -> fail("False is never true"))
			.elseDo(() -> itWorks = true);
		assertTrue(itWorks);

		itWorks = false;
		When.value(23)
			.equals(20 + 4, () -> fail("23 does not equals 20 + 4"))
			.equals(20 + 3, () -> itWorks = true)
			.elseDo(() -> itWorks = false);
		assertTrue(itWorks);

		itWorks = false;
		When.value("A String")
			.is(Integer.class, () -> fail("String is not an integer"))
			.is(String.class, () -> itWorks = true)
			.is(Character.class, () -> fail("String is not a character"));
		assertTrue(itWorks);
	}
}
