package com.fredrikw.fructose.test.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fredrikw.fructose.ml.math.NNTensor;

public class NNTensorTest {
	@Test
	public void test() {
		NNTensor vec = new NNTensor(8, 5, 3);
		System.out.println(vec);
		
		NNTensor mat1 = new NNTensor(new float[][] {
			{1, 4, 6},
			{5, 7, 2}
		});
		System.out.println(mat1);
		
		NNTensor mat2 = new NNTensor(new float[] {1, 4, 6, 5, 7, 2}, new int[] {3, 2});
		System.out.println(mat2);
		
		assertEquals(mat1, mat2);
	}
}
