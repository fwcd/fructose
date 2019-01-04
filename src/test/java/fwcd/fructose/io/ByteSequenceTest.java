package fwcd.fructose.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteSequenceTest {
	@Test
	public void test() {
		ByteSequence seq = new ByteSequence(5);
		assertEquals("101", seq.toBitString());
	}
}
