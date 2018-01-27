package com.fredrikw.fructose.ml.rl.qlearn.as;

import java.util.Map;

import com.fredrikw.fructose.Distribution;
import com.fredrikw.fructose.ml.rl.qlearn.env.QAction;

/**
 * Uses the given Q-values as a (softmaxed) probability
 * distribution to fetch a value. This has the advantage
 * over greedy approaches that it does not only consider
 * the maximum value.
 * 
 * @author Fredrik
 *
 */
public class BoltzmannSelector<A extends QAction> implements QActionSelector<A> {
	private static final long serialVersionUID = 5372118272093913864L;

	@Override
	public A selectAction(Map<A, Double> actions) {
		return new Distribution<>(actions, true).pickStochastically();
	}
}
