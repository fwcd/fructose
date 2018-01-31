package com.fwcd.fructose.ml.rl.qlearn.core;

import java.io.Serializable;

import com.fwcd.fructose.ml.rl.qlearn.env.QAction;
import com.fwcd.fructose.ml.rl.qlearn.env.QState;

public interface QFunction<S extends QState<S, A>, A extends QAction> extends Serializable {
	double predict(S state, A action);
	
	void teach(S state, A action, double output);
}
