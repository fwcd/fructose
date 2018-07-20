package com.fwcd.fructose.test.unittest;

import static com.fwcd.fructose.test.utils.TestUtils.approxEquals;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import com.fwcd.fructose.geometry.DoubleVector;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.geometry.Vector3D;
import com.fwcd.fructose.math.Numbers;
import com.fwcd.fructose.math.Real;
import com.fwcd.fructose.math.Vector;

import org.junit.Test;

public class VectorTest {
	@Test
	public void testDoubleVector() {
		double tol = 0.0001D; // Floating-point tolerance
		assertThat(vec2(4, 3), approxEquals(vec2(4, 3), tol));
		assertThat(vec2(4, 3).add(vec2(1, 2)), approxEquals(vec2(5, 5), tol));
		assertThat(vec2(9, 2).sub(2, 1), approxEquals(vec2(7, 1), tol));
		assertThat(vec2(8, 2).withX(9), approxEquals(vec2(9, 2), tol));
		assertThat(vec2(8, 2).withY(9), approxEquals(vec2(8, 9), tol));
		assertEquals(vec3(1, 0, 0).dot(vec3(0, 1, 0)), 0, 0.01D);
		assertNotEquals(vec2(4, 3), approxEquals(new DoubleVector(4, 3), tol));
	}
	
	@Test
	public void testBoxedVector() {
		assertThat(boxVec(4, 3), approxEquals(boxVec(4, 3), 0.1D));
		assertThat(boxVec(4, 3).add(boxVec(1, 2)), approxEquals(boxVec(5, 5), 0.1D));
		assertThat(boxVec(9, 2).sub(boxVec(2, 1)), approxEquals(boxVec(7, 1), 0.1D));
		assertEquals(boxVec(2, 5, 4).dot(boxVec(1, 2, 3)).value(), 24, 0.1D);
	}
	
	private Vector<Real> boxVec(double... values) {
		return Numbers.realVector(values);
	}
	
	private Vector2D vec2(double x, double y) {
		return new Vector2D(x, y);
	}
	
	private Vector3D vec3(double x, double y, double z) {
		return new Vector3D(x, y, z);
	}
}
