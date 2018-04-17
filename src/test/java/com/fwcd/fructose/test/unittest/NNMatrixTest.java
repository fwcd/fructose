package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fwcd.fructose.ml.math.NNMatrix;
import com.fwcd.fructose.ml.math.NNVector;

public class NNMatrixTest {
	@Test
	public void test() {
		NNMatrix result = new NNMatrix(new float[][] {
				{3, 2, 1},
				{1, 0, 2}
		}).multiply(new NNMatrix(new float[][] {
				{1, 2},
				{0, 1},
				{4, 0}
		}));
		assertTrue(new NNMatrix(new float[][] {
				{7, 8},
				{9, 2}
		}).equals(result));
		assertTrue(new NNMatrix(new float[][] {
				{1, 2},
				{3, 4},
				{5, 6}
		}).transpose().equals(new NNMatrix(new float[][] {
				{1, 3, 5},
				{2, 4, 6}
		})));
		assertTrue(new NNVector(4, 5, 6).transpose().transpose().asVector().equals(new NNVector(4, 5, 6)));
	}
}
