package com.fredrikw.fructose.game.minimax;

import java.util.Collections;
import java.util.Comparator;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

import com.fredrikw.fructose.game.GameMove;
import com.fredrikw.fructose.game.GameRole;
import com.fredrikw.fructose.game.GameState;
import com.fredrikw.fructose.game.MoveEvaluator;
import com.fredrikw.fructose.game.TemplateGameAI;
import com.fredrikw.fructose.game.WinEvaluator;
import com.fredrikw.fructose.time.Timer;

/**
 * The classic two-player, minimax algorithm
 * without alpha-beta. This implementation
 * exists mostly just for educational purposes,
 * for any production use I highly recommed
 * {@link AlphaBeta} instead.
 * 
 * @author Fredrik
 *
 */
public class Minimax extends TemplateGameAI {
	private MoveEvaluator evaluator;
	private int depth = 0;
	
	/**
	 * Creates a new Minimax that attempts to
	 * search the entire game tree and analyze
	 * based off winners. This is only suitable
	 * for very simple games like Tic-Tac-Toe.
	 */
	public Minimax() {
		this(new WinEvaluator(), Integer.MAX_VALUE);
	}
	
	public Minimax(MoveEvaluator evaluator, int depth) {
		this.evaluator = evaluator;
		this.depth = depth;
	}
	
	@Override
	protected GameMove selectMove(GameState game, long softMaxTime) {
		if (!game.getCurrentRole().hasOpponent()) {
			throw new IllegalStateException("Minimax can only operate on two-player games!");
		}
		
		Timer timer = new Timer();
		timer.start(softMaxTime);
		
		Comparator<GameMove> comparator =
				(a, b) -> Double.compare(
						minimax(game.getCurrentRole(), game, a, depth, timer),
						minimax(game.getCurrentRole(), game, b, depth, timer)
				);
		
		return Collections.max(game.getLegalMoves(), comparator);
	}
	
	private double minimax(
			GameRole role,
			GameState gameBeforeMove,
			GameMove move,
			int decrementalDepth,
			Timer timer
	) {
		GameState gameAfterMove = gameBeforeMove.spawnChild(move);
		
		if (!timer.isRunning() || decrementalDepth == 0 || gameAfterMove.isGameOver()) {
			return evaluator.rate(role, gameBeforeMove, gameAfterMove, move, depth - decrementalDepth);
		} else {
			DoubleStream childRatings = gameAfterMove
					.getLegalMoves()
					.stream()
					.mapToDouble(childMove -> minimax(role, gameAfterMove, childMove, decrementalDepth - 1, timer));
			
			OptionalDouble result;
			
			if (gameAfterMove.getCurrentRole().equals(role)) {
				result = childRatings.max();
			} else {
				result = childRatings.min();
			}
			
			return result.orElse(evaluator.rate(role, gameBeforeMove, gameAfterMove, move, depth - decrementalDepth));
		}
	}
}
