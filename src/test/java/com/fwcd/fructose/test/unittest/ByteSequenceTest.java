package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fwcd.fructose.io.ByteSequence;

public class ByteSequenceTest {
	@Test
	public void test() {
		ByteSequence seq = new ByteSequence(5);
		assertEquals("101", seq.toBitString());
	}
}
