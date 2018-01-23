package com.fredrikw.fructose.test.demo;

import com.fredrikw.fructose.ml.neural.LayeredNetwork;

public class LayeredNetworkDemo {
	public static void main(String[] args) {
		LayeredNetwork<?, ?> test = new LayeredNetwork.Builder()
				.learningRate(1.0D)
				.inLayer(null) // TODO
				.layer(null) // TODO
				.layer(null) // TODO
				.outLayer(null) // TODO
				.build();
	}
}
