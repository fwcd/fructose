package com.fredrikw.fructose.game;

import java.util.List;
import java.util.Random;

public class RandomMoveChooser implements MoveChooser {
	private final Random random = new Random();
	
	@Override
	public GameMove chooseMove(GameState game) {
		List<? extends GameMove> moves = game.getLegalMoves();
		return moves.get(random.nextInt(moves.size()));
	}
}
