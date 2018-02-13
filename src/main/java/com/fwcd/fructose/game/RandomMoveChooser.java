package com.fwcd.fructose.game;

import java.util.List;
import java.util.Random;

public class RandomMoveChooser<M extends GameMove, R extends GameRole> implements MoveChooser<M, R> {
	private final Random random = new Random();
	
	@Override
	public M chooseMove(GameState<M, R> game) {
		List<? extends M> moves = game.getLegalMoves();
		return moves.get(random.nextInt(moves.size()));
	}
}
