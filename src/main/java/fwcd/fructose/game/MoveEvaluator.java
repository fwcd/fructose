package fwcd.fructose.game;

/**
 * A move evaluator that can rate moves given
 * a board position. Implementations should provide
 * domain-specific knowledge about the game.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface MoveEvaluator<M extends GameMove, R extends GameRole> {
	/**
	 * Rates a move given a state of the game. The evaluation should
	 * always happen in favor of the given role.
	 * 
	 * @param gameBeforeMove - The game state before the move
	 * @param gameAfterMove - The game state after the move
	 * @param move - The move to be evaluated
	 * @return A rating of the given move on the board
	 */
	double rate(
			R role,
			GameState<? extends M, ? extends R> gameBeforeMove,
			GameState<? extends M, ? extends R> gameAfterMove,
			M move,
			double incrementalDepth
	);
}
