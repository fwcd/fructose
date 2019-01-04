package fwcd.fructose;

import static fwcd.fructose.test.TestUtils.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;

public class SingleIteratorTest {
	@Test
	public void testSingleIterator() {
		SingleIterator<Unit> nullIterator = new SingleIterator<>(null);
		assertTrue(nullIterator.hasNext());
		assertNull(nullIterator.next());
		assertFalse(nullIterator.hasNext());
		assertThrows(NoSuchElementException.class, nullIterator::next);
	}
}
