package com.fwcd.fructose.test.unittest;

import static com.fwcd.fructose.test.utils.TestUtils.approxEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import com.fwcd.fructose.geometry.DoubleMatrix;
import com.fwcd.fructose.math.Complex;
import com.fwcd.fructose.math.Matrix;
import com.fwcd.fructose.math.Numbers;
import com.fwcd.fructose.math.Real;

import org.junit.Test;

public class MatrixTest {
	@Test
	public void testDoubleMatrix() throws InterruptedException {
		DoubleMatrix mat1 = mat(new double[][] {
			{4, 2, 5},
			{1, 1, 9}
		});
		assertThat(mat1.transpose(), approxEquals(mat(new double[][] {
			{4, 1},
			{2, 1},
			{5, 9}
		}), 0.01D));
		assertThat(mat1.minor(0, 0), approxEquals(mat(new double[][] {
			{1, 9}
		}), 0.01D));
		
		DoubleMatrix mat2 = mat(new double[][] {
			{5, 1, 8, 3},
			{1, 0, 2, 1},
			{1, 0, 0, 2},
			{4, 6, 5, 7}
		});
		assertTrue(mat2.inverse().multiply(mat2).isIdentity(0.0001D));
		assertEquals(mat2.determinant(), 39, 0.0001D);
	}
	
	@Test
	public void testRealMatrix() throws InterruptedException {
		Matrix<Real> mat1 = rMat(new double[][] {
			{4, 2, 5},
			{1, 1, 9}
		});
		assertThat(mat1.transpose(), approxEquals(rMat(new double[][] {
			{4, 1},
			{2, 1},
			{5, 9}
		}), 0.0001D));
		assertThat(mat1.minor(0, 0), approxEquals(rMat(new double[][] {
			{1, 9}
		}), 0.0001D));
		
		assertEquals(rMat(new double[][] {
			{-5, 1},
			{8, 7}
		}).determinant().value(), mat(new double[][] {
			{-5, 1},
			{8, 7}
		}).determinant(), 0.0001D);
		
		Matrix<Real> mat2 = rMat(new double[][] {
			{5, 1, 8, 3},
			{1, 0, 2, 1},
			{1, 0, 0, 2},
			{4, 6, 5, 7}
		});
		assertThat(mat2.minor(1, 1), approxEquals(rMat(new double[][] {
			{5, 8, 3},
			{1, 0, 2},
			{4, 5, 7}
		}), 0.0001D));
		assertEquals(mat2.determinant().value(), 39, 0.0001D);
		
		// Test Tensor product/Kronecker product
		assertThat(rMat(new double[][] {
			{1, 2},
			{3, 4},
			{5, 6}
		}).kronecker(rMat(new double[][] {
			{7, 8},
			{9, 0}
		})), approxEquals(rMat(new double[][] {
			{ 7,  8, 14, 16},
			{ 9,  0, 18,  0},
			{21, 24, 28, 32},
			{27,  0, 36,  0},
			{35, 40, 42, 48},
			{45,  0, 54,  0}
		}), 0.1D));
	}
	
	@Test
	public void testComplexMatrix() {
		Matrix<Complex> mat1 = cMat(new double[][][] {
			{{2, 3}, {1, 0}},
			{{3, 2}, {1, 1}}
		});
		Matrix<Complex> mat2 = cMat(new double[][][] {
			{{1, -2}},
			{{-1, 2}}
		});
		assertThat(mat1.multiply(mat2), approxEquals(cMat(new double[][][] {
			{{7, 1}},
			{{4, -3}}
		}), 0.1D));
	}
	
	private Matrix<Real> rMat(double[][] values) {
		return Numbers.realMatrix(values);
	}
	
	
	private Matrix<Complex> cMat(double[][][] values) {
		return Numbers.complexMatrix(values);
	}
	
	public DoubleMatrix mat(double[][] values) {
		return new DoubleMatrix(values);
	}
}
