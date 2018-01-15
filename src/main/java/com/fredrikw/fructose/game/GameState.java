package com.fredrikw.fructose.game;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fredrikw.fructose.structs.TreeNode;

/**
 * A mutable state in the game.
 * 
 * @author Fredrik
 * 
 * @param T - This implementation type
 */
public interface GameState extends TreeNode {
	/**
	 * Fetches the legal moves. Caching these might
	 * be a good idea.
	 * 
	 * @return The list of possible moves
	 */
	List<? extends GameMove> getLegalMoves();
	
	/**
	 * Fetches the winners, if there are any.
	 * 
	 * @return The set of winners
	 */
	Set<? extends GameRole> getWinners();
	
	/**
	 * Executes the given move.
	 * 
	 * @param move - The game move to be performed
	 */
	void perform(GameMove move);
	
	/**
	 * Fetches the current player/role (the one that
	 * has to perform the next move).
	 * 
	 * @return The current role
	 */
	GameRole getCurrentRole();
	
	/**
	 * Deep copies this game state.
	 * 
	 * @return An independent copy of this state
	 */
	GameState copy();
	
	/**
	 * @return Whether this game is over
	 */
	default boolean isGameOver() {
		return !getWinners().isEmpty() || getLegalMoves().isEmpty();
	}
	
	default GameState spawnChild(GameMove move) {
		GameState child = copy();
		child.perform(move);
		return child;
	}
	
	default boolean isLegal(GameMove move) {
		return getLegalMoves().contains(move);
	}

	@Override
	default List<? extends TreeNode> getChildren() {
		return getLegalMoves().stream()
				.map(this::spawnChild)
				.collect(Collectors.toList());
	}
}
