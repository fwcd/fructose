package fwcd.fructose.ml.rl.qlearn.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fwcd.fructose.ml.rl.qlearn.as.QActionSelector;
import fwcd.fructose.ml.rl.qlearn.env.QAction;
import fwcd.fructose.ml.rl.qlearn.env.QState;
import fwcd.fructose.ml.rl.qlearn.env.QStep;

public class CompositeLearner<S extends QState<S, A>, A extends QAction> implements QLearner<S, A> {
	private static final long serialVersionUID = -4442218986667234464L;
	private final QActionSelector<A> actionSelector;
	private final QFunction<S, A> qFunction;
	
	public CompositeLearner(QFunction<S, A> qFunction, QActionSelector<A> actionSelector) {
		this.qFunction = qFunction;
		this.actionSelector = actionSelector;
	}

	@Override
	public QFunction<S, A> getQFunction() {
		return qFunction;
	}
	
	@Override
	public QStep<S, A> pickStep(S state, int index) {
		List<? extends A> actions = state.getActions();
		Map<A, Double> qValueMap = new HashMap<>();
		
		for (A action : actions) {
			qValueMap.put(action, qFunction.predict(state, action));
		}
		
		A selectedAction = actionSelector.selectAction(qValueMap);
		return new QStep<>(selectedAction, qValueMap.get(selectedAction), index);
	}

	@Override
	public double maxQ(S state) {
		return state.getActions()
				.stream()
				.mapToDouble(action -> qFunction.predict(state, action))
				.max()
				.orElseThrow(() -> new IllegalStateException("State has no actions."));
	}
}
