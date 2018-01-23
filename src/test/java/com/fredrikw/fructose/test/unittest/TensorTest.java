package com.fredrikw.fructose.test.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fredrikw.fructose.math.Tensor;

public class TensorTest {
	@Test
	public void test() {
		Tensor vec = new Tensor(8, 5, 3);
//		System.out.println(vec);
		
		Tensor mat1 = new Tensor(new double[][] {
			{1, 4, 6},
			{5, 7, 2}
		});
//		System.out.println(mat1);
		
		Tensor mat2 = new Tensor(new double[] {1, 4, 6, 5, 7, 2}, new int[] {3, 2});
//		System.out.println(mat2);
		
		assertEquals(mat1, mat2);
		assertEquals(vec.asVector().asTensor(), vec);
		assertEquals(mat1, mat1.asMatrix().asTensor());
	}
}
