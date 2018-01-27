package com.fredrikw.fructose.ml.rl.qlearn.as;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.fredrikw.fructose.ml.rl.qlearn.env.QAction;

/**
 * Randomly selects actions and thus is only suited
 * for circumstances where pure exploration is required
 * (for example to sample the state space in order to
 * fill an experience buffer in a deep-Q-network.
 * 
 * @author Fredrik
 *
 */
public class RandomSelector<A extends QAction> implements QActionSelector<A> {
	private static final long serialVersionUID = -8535745687847130018L;

	@Override
	public A selectAction(Map<A, Double> actions) {
		int dest = ThreadLocalRandom.current().nextInt(actions.size());
		int i = 0;
		
		for (A action : actions.keySet()) {
			if (i == dest) {
				return action;
			}
			i++;
		}
		
		throw new IllegalStateException("Unreachable code: Destination index should always be bounded by the map size.");
	}
}
