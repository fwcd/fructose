package com.fwcd.fructose.test.unittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	NNMatrixTest.class,
	TensorTest.class,
	MathGraphTest.class,
	MatrixTest.class,
	TableTest.class,
	ComplexTest.class,
	PrimitiveListTest.class,
	VectorTest.class
})
public class AllTests {

}
