package fwcd.fructose.text;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import fwcd.fructose.text.Password;

import org.junit.Test;

public class PasswordTest {
	@Test
	public void testPassword() {
		Password a = Password.newEmpty();
		assertEquals(0, a.getLength());
		
		Password b = Password.of('T', 'e', 's', 't');
		assertEquals(4, b.getLength());
		assertEquals('T', b.getChar(0));
		assertEquals('s', b.getChar(2));
		assertNotEquals("Test", b.toString());
		
		Password c = Password.of('T', 'e', 's', 't');
		assertNotEquals(b, c);
		c.erase();
		assertEquals(4, c.getLength());
		for (int i=0; i<c.getLength(); i++) {
			assertEquals(0, c.getChar(i));
		}
		c.fillWith('x');
		assertArrayEquals(c.get(), new char[] {'x', 'x', 'x', 'x'});
		c.set('p', 'a', 's', 's', 'w', 'o', 'r', 'd');
		assertArrayEquals(c.get(), new char[] {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'});
	}
}
