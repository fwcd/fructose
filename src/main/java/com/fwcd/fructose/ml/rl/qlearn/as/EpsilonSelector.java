package com.fwcd.fructose.ml.rl.qlearn.as;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.fwcd.fructose.ml.rl.qlearn.env.QAction;

/**
 * Combines a random approach with another selector. It chooses
 * the other selector by default, but has a probability to yield
 * a random result. When used with a {@link GreedySelector}, this
 * is one of the most commonly used techniques, despite it's flaws.
 * 
 * @author Fredrik
 */
public class EpsilonSelector<A extends QAction> implements QActionSelector<A> {
	private static final long serialVersionUID = -5285114622396232218L;
	private final RandomSelector<A> randomSelector = new RandomSelector<>();
	private final QActionSelector<A> defaultSelector;
	
	private double epsilon; // Between 0 (not random) and 1 (very random)
	
	/**
	 * Creates a new epsilon-greedy selector.
	 */
	public EpsilonSelector() {
		this(new GreedySelector<>());
	}
	
	public EpsilonSelector(QActionSelector<A> defaultSelector) {
		this.defaultSelector = defaultSelector;
		epsilon = 0.6D;
	}
	
	public EpsilonSelector(QActionSelector<A> defaultSelector, double epsilon) {
		this.defaultSelector = defaultSelector;
		this.epsilon = epsilon;
	}
	
	@Override
	public A selectAction(Map<A, Double> actions) {
		if (ThreadLocalRandom.current().nextDouble() < epsilon) {
			return randomSelector.selectAction(actions);
		} else {
			return defaultSelector.selectAction(actions);
		}
	}
}
