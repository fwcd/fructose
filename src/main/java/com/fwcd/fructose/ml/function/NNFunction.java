package com.fwcd.fructose.ml.function;

import com.fwcd.fructose.function.FloatUnaryOperator;

/**
 * Common functions used in machine learning.
 * 
 * @author Fredrik
 *
 */
public enum NNFunction implements DiffFunction {
	SIGMOID(
			x -> 1 / (1 + (float) Math.exp(-x)),
			out -> out * (1 - out),
			true
	),
	RELU(
			x -> Math.max(0, x),
			out -> out <= 0 ? 0 : 1,
			true
	),
	TANH(
			x -> (float) Math.tanh(x),
			out -> 1 - (out * out),
			true
	),
	LEAKY_RELU(
			x -> Math.max(0.1F * x, x),
			out -> out <= 0 ? 0.1F : 1,
			true
	);
	
	private final FloatUnaryOperator func;
	private final FloatUnaryOperator deriv;
	private final boolean derivativeUsesCachedF;
	
	private NNFunction(FloatUnaryOperator func, FloatUnaryOperator deriv, boolean derivativeUsesCachedF) {
		this.func = func;
		this.deriv = deriv;
		this.derivativeUsesCachedF = derivativeUsesCachedF;
	}

	@Override
	public float f(float x) {
		return func.applyAsFloat(x);
	}

	@Override
	public float df(float x) {
		if (derivativeUsesCachedF) {
			return deriv.applyAsFloat(f(x));
		} else {
			return deriv.applyAsFloat(x);
		}
	}

	@Override
	public float dfUsingF(float f) {
		if (derivativeUsesCachedF) {
			return deriv.applyAsFloat(f);
		} else {
			return DiffFunction.super.dfUsingF(f);
		}
	}

	@Override
	public boolean canComputeDfUsingF() {
		return derivativeUsesCachedF;
	}
}
