package com.fwcd.fructose.game;

/**
 * Represents a game role or a "player" in the abstract sense.
 * Chess for example would have two instances of this class
 * (white and black).<br><br>
 * 
 * The state of a GameRole should not change throughout
 * the game (as opposed to a player with play money for example).
 * Thus it is highly recommended for the implementation to
 * be immutable, though this is not an absolute requirement.
 * 
 * @author Fredrik
 *
 */
public interface GameRole {
	/**
	 * @return The opponent or null if there is none
	 */
	GameRole getOpponent();
	
	boolean hasOpponent();
}
