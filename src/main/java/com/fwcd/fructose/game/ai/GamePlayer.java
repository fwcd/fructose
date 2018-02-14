package com.fwcd.fructose.game.ai;

import com.fwcd.fructose.game.GameMove;
import com.fwcd.fructose.game.GameRole;
import com.fwcd.fructose.game.GameState;
import com.fwcd.fructose.game.MoveChooser;

/**
 * A smart move chooser (i.e. an AI).
 * Thus it provides additional methods like time limits.
 * 
 * @author Fredrik
 *
 */
public interface GamePlayer<M extends GameMove, R extends GameRole> extends MoveChooser<M, R> {
	/**
	 * Sets the soft time limit. Usually it is up to the
	 * algorithm to <i>try</i> to finish in this time period.
	 * 
	 * @param ms - The soft time limit in milliseconds
	 */
	void setSoftMaxTime(long ms);
	
	/**
	 * Sets the hard time limit. Although it is not an absolute
	 * requirement that the player responds in this exact time period,
	 * it <i>should</i> do so in any usual environment.
	 * 
	 * @param ms - The hard max time limit
	 */
	void setHardMaxTime(long ms);
	
	/**
	 * Sets a "level" property that could be used
	 * to represent the tree search depth or something similar.
	 * 
	 * <p><b>Does nothing by default.</b></p>
	 * 
	 * @param level - The value used
	 */
	default void setLevel(int level) {}
	
	/**
	 * Returns a "level" property that could be used
	 * to represent the tree search depth or something similar.
	 * 
	 * <p><b>Always returns 0 by default.</b></p>
	 * 
	 * @return The "level" value
	 */
	default int getLevel() { return 0; }
	
	/**
	 * Notifies the player that a game has started thus providing the
	 * player with an opportunity to prepare.
	 * 
	 * <p><b>Note that it is not guaranteed that this method
	 * will ever be called.</b></p>
	 * 
	 * @param role - The role of this AI in the game
	 */
	default void onGameStart(GameState<M, R> intialState, R role) {}
	
	/**
	 * Notifies the player that a game has ended thus providing the
	 * player with an opportunity to "learn" from wins/losses.
	 * 
	 * <p><b>Note that it is not guaranteed that this method
	 * will ever be called.</b></p>
	 * 
	 * @param finalState - The final state of the game
	 * @param role - The role of this AI in the game
	 */
	default void onGameEnd(GameState<M, R> finalState, R role) {}
}
