package com.fredrikw.fructose.ml.rl.qlearn.core;

import java.io.Serializable;

import com.fredrikw.fructose.ml.rl.qlearn.env.QAction;
import com.fredrikw.fructose.ml.rl.qlearn.env.QState;
import com.fredrikw.fructose.ml.rl.qlearn.env.QStep;

public interface QAgent<S extends QState<S, A>, A extends QAction> extends Serializable {
	QStep<S, A> pickStep(S state, int index);
}
