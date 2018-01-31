package com.fwcd.fructose.ml.rl.qlearn.core;

import java.io.Serializable;

import com.fwcd.fructose.ml.rl.qlearn.env.QAction;
import com.fwcd.fructose.ml.rl.qlearn.env.QState;
import com.fwcd.fructose.ml.rl.qlearn.env.QStep;

public interface QAgent<S extends QState<S, A>, A extends QAction> extends Serializable {
	QStep<S, A> pickStep(S state, int index);
}
