package fwcd.fructose.game;

/**
 * Represents a move chooser that can pick
 * a move from a game state.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface MoveChooser<M extends GameMove, R extends GameRole> {
	M chooseMove(GameState<M, R> game);
}
