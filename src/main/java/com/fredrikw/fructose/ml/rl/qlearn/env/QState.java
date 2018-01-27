package com.fredrikw.fructose.ml.rl.qlearn.env;

import java.util.List;

/**
 * An immutable state in the game.
 * 
 * @author Fredrik
 *
 * @param <S> - The implementation type
 * @param <A> - The action type
 */
public interface QState<S extends QState<S, A>, A extends QAction> {
	List<? extends A> getActions();
	
	QStepResult<S, A> spawnChild(QStep<S, A> step);
	
	boolean isFinalState();
}
