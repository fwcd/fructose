package com.fredrikw.fructose.test.unittest;

import org.junit.Test;

import com.fredrikw.fructose.ml.function.NNFunction;
import com.fredrikw.fructose.ml.function.NNWeightInit;
import com.fredrikw.fructose.ml.math.NNVector;
import com.fredrikw.fructose.ml.neural.LabelledData;
import com.fredrikw.fructose.ml.neural.Perceptron;

public class PerceptronTest {
	@Test
	public void test() {
		Perceptron net = new Perceptron.Builder()
				.weightInit(NNWeightInit.XAVIER)
				.activationFunc(NNFunction.LEAKY_RELU)
				.learnFactor(0.1F)
				.layer(5)
				.layer(6)
				.layer(4)
				.layer(3)
				.build();
		
		final NNVector in = new NNVector(2, 3, 7, 1, 8);
		final NNVector out = new NNVector(1, 0, 0);
		final LabelledData<NNVector, NNVector> testData = new LabelledData<>(in, out);
		
		System.out.println(net.compute(in));
		
		for (int i=0; i<1000; i++) {
			net.backprop(testData);
			System.out.println("1, 0, 0 => " + net.cost(testData));
		}
		
		System.out.println(net.compute(in));
	}
}
