package com.fredrikw.fructose.test.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fredrikw.fructose.geometry.Matrix;

public class MatrixTest {
	@Test
	public void test() throws InterruptedException {
		Matrix mat1 = new Matrix(new double[][] {
			{4, 2, 5},
			{1, 1, 9}
		});
		assertEquals(mat1.transpose(), new Matrix(new double[][] {
			{4, 1},
			{2, 1},
			{5, 9}
		}));
		assertEquals(mat1.minor(0, 0), new Matrix(new double[][] {
			{1, 9}
		}));
		
		Matrix mat2 = new Matrix(new double[][] {
				{5, 1, 8, 3},
				{1, 0, 2, 1},
				{1, 0, 0, 2},
				{4, 6, 5, 7}
		});
		assertTrue(mat2.inverse().multiply(mat2).isIdentity(0.0001D));
		assertEquals(mat2.determinant(), 39, 0.0001D);
	}
}
