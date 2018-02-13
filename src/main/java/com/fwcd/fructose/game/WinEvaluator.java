package com.fwcd.fructose.game;

import java.util.Set;

/**
 * A very basic evaluator that only rates moves based
 * on direct wins and loses.
 * 
 * @author Fredrik
 *
 */
public class WinEvaluator<M extends GameMove, R extends GameRole> implements MoveEvaluator<M, R> {
	@Override
	public double rate(
			R role,
			GameState<? extends M, ? extends R> gameBeforeMove,
			GameState<? extends M, ? extends R> gameAfterMove,
			M move,
			double incrementalDepth
	) {
		Set<? extends GameRole> winners = gameAfterMove.getWinners();
		
		if (winners.contains(role)) {
			return Double.MAX_VALUE / incrementalDepth;
		} else if (!winners.isEmpty()) {
			return -(Double.MAX_VALUE / incrementalDepth);
		} else {
			return 0;
		}
	}
}
