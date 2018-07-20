package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.fwcd.fructose.math.DoubleTensor;

public class TensorTest {
	@Test
	public void testDoubleTensor() {
		DoubleTensor vec = new DoubleTensor(8, 5, 3);
		DoubleTensor mat1 = new DoubleTensor(new double[][] {
			{1, 4, 6},
			{5, 7, 2}
		});
		DoubleTensor mat2 = new DoubleTensor(new double[] {1, 4, 6, 5, 7, 2}, new int[] {3, 2});
		
		assertEquals(mat1, mat2);
		assertEquals(vec.asVector().asTensor(), vec);
		assertEquals(mat1, mat1.asMatrix().asTensor());
	}
}
