package com.fredrikw.fructose.game;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fredrikw.fructose.Copyable;
import com.fredrikw.fructose.structs.TreeNode;

/**
 * A mutable state in the game.
 * 
 * @author Fredrik
 * 
 * @param M - The move type
 * @param R - The role type
 */
public interface GameState<M extends GameMove, R extends GameRole> extends TreeNode, Copyable<GameState<M, R>> {
	/**
	 * Fetches the legal moves. Caching these might
	 * be a good idea.
	 * 
	 * @return The list of possible moves
	 */
	List<? extends M> getLegalMoves();
	
	/**
	 * Fetches the winners, if there are any.
	 * 
	 * @return The set of winners
	 */
	Set<? extends R> getWinners();
	
	/**
	 * Fetches the current player/role (the one that
	 * has to perform the next move).
	 * 
	 * @return The current role
	 */
	R getCurrentRole();
	
	/** Executes the given move.
	 * 
	 * @param move - The game move to be performed
	 */
	void perform(M move);
	
	/**
	 * Deep copies this game state.
	 * 
	 * @return An independent copy of this state
	 */
	@Override
	GameState<M, R> copy();
	
	/**
	 * @return Whether this game is over
	 */
	default boolean isGameOver() {
		return !getWinners().isEmpty() || getLegalMoves().isEmpty();
	}
	
	default GameState<M, R> spawnChild(M move) {
		GameState<M, R> child = copy();
		child.perform(move);
		return child;
	}
	
	default boolean isLegal(M move) {
		return getLegalMoves().contains(move);
	}

	@Override
	default List<? extends TreeNode> getChildren() {
		return getLegalMoves().stream()
				.map(this::spawnChild)
				.collect(Collectors.toList());
	}
}
