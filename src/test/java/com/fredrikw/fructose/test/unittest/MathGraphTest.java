package com.fredrikw.fructose.test.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fredrikw.fructose.math.graph.ConstScalar;
import com.fredrikw.fructose.math.graph.Scalar;
import com.fredrikw.fructose.math.graph.VarScalar;

public class MathGraphTest {
	@Test
	public void test() {
		VarScalar x = new VarScalar("x");
		VarScalar y = new VarScalar("y");
		ConstScalar one = ConstScalar.ONE;
		ConstScalar two = new ConstScalar(2);
		
//		System.out.println(Math.pow(-4, -2));
		
		Scalar test = x.multiply(y.pow(two)).sub(x);
		
		for (int i=-2; i<2; i++) {
			x.set(i * 3);
			y.set(i * 2);
			
			assertEquals(test.partialDerivative(x).compute(), y.pow(two).sub(one).compute());
			assertEquals(test.partialDerivative(y).compute(), two.multiply(x).multiply(y).compute());
		}
	}
}
