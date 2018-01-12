package com.fredrikw.fructose.game;

/**
 * Represents a move chooser that can pick
 * a move from a game state.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface MoveChooser {
	GameMove chooseMove(GameState game);
}
