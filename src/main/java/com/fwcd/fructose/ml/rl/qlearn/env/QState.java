package com.fwcd.fructose.ml.rl.qlearn.env;

import java.util.List;

import com.fwcd.fructose.ml.math.NNVector;

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
	
	default NNVector toNeurons(A action) {
		throw new UnsupportedOperationException("QState does not support neurons.");
	}
}
