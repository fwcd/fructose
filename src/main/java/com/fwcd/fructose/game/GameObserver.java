package com.fwcd.fructose.game;

public interface GameObserver<M extends GameMove, R extends GameRole> {
	default void onGameStart(GameState<M, R> initialState) {}
	
	default void onGameMove(GameState<M, R> gameBeforeMove, M move) {}
	
	default void onGameEnd(GameState<M, R> finalState) {}
}
