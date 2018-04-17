package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fwcd.fructose.geometry.Vector;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.geometry.Vector3D;

public class VectorTest {
	@Test
	public void test() {
		assertEquals(new Vector2D(4, 3), new Vector2D(4, 3));
		assertTrue(new Vector2D(4, 3).equals(new Vector2D(4, 3), 0.1D));
		assertFalse(new Vector2D(4, 3).equals(new Vector2D(4, 8), 0.1D));
		assertEquals(new Vector2D(4, 3).add(new Vector2D(1, 2)), new Vector2D(5, 5));
		assertEquals(new Vector2D(9, 2).sub(2, 1), new Vector2D(7, 1));
		assertEquals(new Vector2D(8, 2).withX(9), new Vector2D(9, 2));
		assertEquals(new Vector2D(8, 2).withY(9), new Vector2D(8, 9));
		assertEquals(new Vector3D(1, 0, 0).dot(new Vector3D(0, 1, 0)), 0, 0.01D);
		assertNotEquals(new Vector2D(4, 3), new Vector(4, 3));
	}
}
