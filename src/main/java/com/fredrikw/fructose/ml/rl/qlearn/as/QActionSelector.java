package com.fredrikw.fructose.ml.rl.qlearn.as;

import java.io.Serializable;
import java.util.Map;

import com.fredrikw.fructose.ml.rl.qlearn.env.QAction;

public interface QActionSelector<A extends QAction> extends Serializable {
	A selectAction(Map<A, Double> actions);
}
