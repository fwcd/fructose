package com.fredrikw.fructose.game;

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
}
