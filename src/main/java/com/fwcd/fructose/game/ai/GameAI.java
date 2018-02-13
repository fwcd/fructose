package com.fwcd.fructose.game.ai;

import com.fwcd.fructose.game.GameMove;
import com.fwcd.fructose.game.GameRole;
import com.fwcd.fructose.game.GameState;
import com.fwcd.fructose.game.MoveChooser;

/**
 * A move chooser that is intended to be used as
 * an AI. Thus it provides additional methods
 * like time limits.
 * 
 * @author Fredrik
 *
 */
public interface GameAI extends MoveChooser {
	/**
	 * Sets the soft time limit. Usually it is up to the
	 * algorithm to <i>try</i> to finish in this time period.
	 * 
	 * @param ms - The soft time limit in milliseconds
	 */
	void setSoftMaxTime(long ms);
	
	/**
	 * Sets the hard time limit. Although it is not an absolute
	 * requirement that the AI responds in this exact time period,
	 * it <i>should</i> do so in any usual environment.
	 * 
	 * @param ms - The hard max time limit
	 */
	void setHardMaxTime(long ms);
	
	/**
	 * Notifies the AI that a game has started thus providing the
	 * AI with an opportunity to prepare.
	 * 
	 * <p><b>Note that it is not guaranteed that this method
	 * will ever be called.</b></p>
	 * 
	 * @param role - The role of this AI in the game
	 */
	default <M extends GameMove, R extends GameRole> void onGameStart(R role) {}
	
	/**
	 * Notifies the AI that a game has ended thus providing the
	 * AI with an opportunity to "learn" from wins/losses.
	 * 
	 * <p><b>Note that it is not guaranteed that this method
	 * will ever be called.</b></p>
	 * 
	 * @param finalState - The final state of the game
	 * @param role - The role of this AI in the game
	 */
	default <M extends GameMove, R extends GameRole> void onGameEnd(GameState<M, R> finalState, R role) {}
}
