package fwcd.fructose;

import static fwcd.fructose.test.TestUtils.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;

public class EitherTest {
	@Test
	public void testEither() {
		Either<String, Integer> a = Either.ofLeft("This is a string");
		assertEquals("This is a string", a.unwrapLeft());
		assertEquals("This is a string", a.getLeft().orElseThrow(NoSuchElementException::new));
		assertTrue(a.getLeft().isPresent());
		assertTrue(!a.getRight().isPresent());
		assertThrows(NoSuchElementException.class, a::unwrapRight);

		a = Either.ofRight(24);
		assertEquals(24, a.get());
		assertEquals(Integer.valueOf(24), a.unwrapRight());
		assertThrows(NoSuchElementException.class, a::unwrapLeft);
	}
}
