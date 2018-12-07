package fwcd.fructose.game;

@FunctionalInterface
public interface MovePruner<M extends GameMove, R extends GameRole> {
	boolean pruneMove(
			R role,
			GameState<? extends M, ? extends R> gameBeforeMove,
			GameState<? extends M, ? extends R> gameAfterMove,
			M move,
			double incrementalDepth
	);
}
