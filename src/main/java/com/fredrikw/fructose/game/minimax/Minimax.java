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
import com.fredrikw.fructose.time.Stopwatch;

/**
 * The classic two-player, minimax algorithm
 * without alpha-beta.
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
		Stopwatch watch = new Stopwatch();
		watch.start();
		
		Comparator<GameMove> comparator =
				(a, b) -> Double.compare(
						minimax(game.getCurrentRole(), game, a, depth, softMaxTime, watch),
						minimax(game.getCurrentRole(), game, b, depth, softMaxTime, watch));
		
		return Collections.max(game.getLegalMoves(), comparator);
	}
	
	private double minimax(
			GameRole role,
			GameState gameBeforeMove,
			GameMove move,
			int decrementalDepth,
			long softMaxTime,
			Stopwatch watch
	) {
		GameState gameAfterMove = gameBeforeMove.spawnChild(move);
		
		if (watch.getMillis() > softMaxTime || decrementalDepth == 0 || gameAfterMove.isGameOver()) {
			return evaluator.rate(role, gameBeforeMove, gameAfterMove, move, depth - decrementalDepth);
		} else {
			DoubleStream childRatings = gameAfterMove.getLegalMoves().parallelStream()
					.mapToDouble(childMove -> minimax(
							role,
							gameAfterMove,
							childMove,
							decrementalDepth - 1,
							softMaxTime,
							watch));
			
			OptionalDouble result;
			
			if (gameAfterMove.getCurrentRole() == role) {
				result = childRatings.max();
			} else {
				result = childRatings.min();
			}
			
			return result.orElse(evaluator.rate(role, gameBeforeMove, gameAfterMove, move, depth - decrementalDepth));
		}
	}
}
