package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fwcd.fructose.math.Complex;

public class ComplexTest {
	@Test
	public void test() {
		Complex test = new Complex(2, 3);
		double tolerance = 0.1;
		
		assertEquals(test.abs(), 3.60555, tolerance);
		assertEquals(test.argument(), 0.9827, tolerance);
		assertEquals(test.absSquared(), Math.pow(test.abs(), 2), tolerance);
		assertEquals(test.add(new Complex(1, 4)), new Complex(3, 7));
		assertEquals(test.conjugate(), new Complex(2, -3));
		assertTrue(test.cos().equals(new Complex(-4.1896, -9.1092), tolerance));
		assertTrue(test.sin().equals(new Complex(9.15, -4.16), tolerance));
		assertTrue(test.multiply(new Complex(4, -8)).equals(new Complex(32, -4), tolerance));
		assertTrue(test.invertImag().equals(test.conjugate()));
		assertTrue(test.exp().equals(new Complex(Math.E).pow(test), tolerance));
		assertTrue(test.pow(4).equals(test.pow(new Complex(4)), tolerance));
		assertTrue(test.divide(new Complex(9, 8)).equals(new Complex(0.289, 0.075), tolerance));
		assertTrue(test.reciprocal().equals(test.pow(-1), tolerance));
		assertTrue(test.square().equals(test.pow(2), tolerance));
		assertTrue(test.tan().equals(new Complex(-0.0037, 1.0032), 0.001));
		assertTrue(test.divide(9).equals(test.divide(new Complex(9)), tolerance));
		assertTrue(test.sub(new Complex(9, 7)).equals(test.add(new Complex(-9, -7)), tolerance));
	}
}
