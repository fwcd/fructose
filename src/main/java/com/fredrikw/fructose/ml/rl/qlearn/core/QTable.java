package com.fredrikw.fructose.ml.rl.qlearn.core;

import com.fredrikw.fructose.ml.rl.qlearn.env.QAction;
import com.fredrikw.fructose.ml.rl.qlearn.env.QState;
import com.fredrikw.fructose.structs.MapTable;
import com.fredrikw.fructose.structs.Table;

/**
 * A very simple Q-function implementation that remembers
 * every state-action combination.
 * 
 * @author Fredrik
 *
 * @param <S>
 * @param <A>
 */
public class QTable<S extends QState<S, A>, A extends QAction> implements QFunction<S, A> {
	private static final long serialVersionUID = -4758437029018633686L;
	private final Table<S, A, Double> data = new MapTable<>();
	
	@Override
	public double predict(S state, A action) {
		return data.getOrDefault(state, action, 0D);
	}

	@Override
	public void teach(S state, A action, double output) {
		data.put(state, action, output);
	}
}
