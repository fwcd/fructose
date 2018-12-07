package fwcd.fructose.ml.rl.qlearn.as;

import java.util.Map;

import fwcd.fructose.ml.rl.qlearn.env.QAction;

/**
 * A greedy selection strategy that picks the
 * maximum Q-value at every step. While it might
 * exploit in the short run, it almost always
 * finds a relatively suboptimal solution.
 * 
 * @author Fredrik
 *
 */
public class GreedySelector<A extends QAction> implements QActionSelector<A> {
	private static final long serialVersionUID = -4191217691081356570L;

	@Override
	public A selectAction(Map<A, Double> actions) {
		A maxAction = null;
		double maxQ = Double.NEGATIVE_INFINITY;
		
		for (A action : actions.keySet()) {
			double q = actions.get(action);
			if (q > maxQ) {
				maxQ = q;
				maxAction = action;
			}
		}
		
		return maxAction;
	}
}
