package fwcd.fructose.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import fwcd.fructose.game.ai.GamePlayer;

/**
 * A class that automatically plays games
 * using {@link GamePlayer} instances.
 * 
 * @author Fredrik
 *
 * @param <M> - The move type
 * @param <R> - The game role type
 */
public class GameDriver<M extends GameMove, R extends GameRole> {
	private final Supplier<GameState<M, R>> gameCreator;
	private final List<GameObserver<M, R>> observers = new ArrayList<>();
	private final Map<R, GamePlayer<M, R>> players = new HashMap<>();

	private long hardMoveTimeLimit = Long.MAX_VALUE;
	private long softMoveTimeLimit = Long.MAX_VALUE;
	private boolean outputToConsole = false;
	
	// TODO: Implement maximum move count
	
	public GameDriver(Supplier<GameState<M, R>> gameCreator) {
		this.gameCreator = gameCreator;
	}
	
	public void setPlayer(R role, GamePlayer<M, R> player) {
		players.put(role, player);
	}
	
	/**
	 * Plays the given amount of matches. The players
	 * are taking turns in the order provided. This
	 * method will "block" until all games are played.
	 * 
	 * <p>Hook observers to receive information about the
	 * played games.</p>
	 * 
	 * @param matches - The amount of matches
	 * @param players - The players used to play the game
	 */
	public synchronized void play(int matches) {
		int playersCount = players.size();
		
		if (playersCount == 0) {
			throw new IllegalArgumentException("Needs at least one player to play a game!");
		}
		
		for (GamePlayer<M, R> ai : players.values()) {
			ai.setHardMaxTime(hardMoveTimeLimit);
			ai.setSoftMaxTime(softMoveTimeLimit);
		}
		
		for (int i=0; i<matches; i++) {
			GameState<M, R> game = gameCreator.get();
			fireStartListeners(game);
			
			while (!game.isGameOver()) {
				GamePlayer<M, R> ai = players.get(game.getCurrentRole());
				
				if (ai == null) {
					throw new IllegalStateException("GameDriver is missing a player for " + game.getCurrentRole().toString());
				}
				
				
				M move = ai.chooseMove(game);
				fireMoveListeners(game, move);
				game.perform(move);
			}
			
			if (outputToConsole) {
				System.out.println(game.getWinners().toString() + " won the " + Integer.toString(i) + ". match");
			}
			
			fireEndListeners(game);
		}
	}
	
	private void fireMoveListeners(GameState<M, R> stateBeforeMove, M move) {
		for (GameObserver<M, R> observer : observers) {
			observer.onGameMove(stateBeforeMove, move);
		}
	}
	
	private void fireStartListeners(GameState<M, R> state) {
		for (GameObserver<M, R> observer : observers) {
			observer.onGameStart(state);
		}
		for (R r : players.keySet()) {
			players.get(r).onGameStart(state, r);
		}
	}
	
	private void fireEndListeners(GameState<M, R> state) {
		for (GameObserver<M, R> observer : observers) {
			observer.onGameEnd(state);
		}
		for (R r : players.keySet()) {
			players.get(r).onGameEnd(state, r);
		}
	}
	
	public void addObserver(GameObserver<M, R> observer) {
		observers.add(observer);
	}
	
	public void removeObserver(GameObserver<M, R> observer) {
		observers.remove(observer);
	}
	
	public void setHardMoveTimeLimit(long ms) {
		hardMoveTimeLimit = ms;
	}
	
	public void setSoftMoveTimeLimit(long ms) {
		softMoveTimeLimit = ms;
	}
	
	public void setOutputToConsole(boolean enabled) {
		outputToConsole = enabled;
	}
}
