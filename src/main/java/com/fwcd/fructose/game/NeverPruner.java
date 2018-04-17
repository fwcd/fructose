package com.fwcd.fructose.game;

public class NeverPruner<M extends GameMove, R extends GameRole> implements MovePruner<M, R> {
	@Override
	public boolean pruneMove(
			R role,
			GameState<? extends M, ? extends R> gameBeforeMove,
			GameState<? extends M, ? extends R> gameAfterMove,
			M move,
			double incrementalDepth
	) {
		return false;
	}
}
