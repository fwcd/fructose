package com.fwcd.fructose.game.ai;

import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

import com.fwcd.fructose.game.GameMove;
import com.fwcd.fructose.game.GameRole;
import com.fwcd.fructose.game.GameState;
import com.fwcd.fructose.game.MoveEvaluator;
import com.fwcd.fructose.game.MovePruner;
import com.fwcd.fructose.game.NeverPruner;
import com.fwcd.fructose.game.WinEvaluator;
import com.fwcd.fructose.time.Timer;

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
public class Minimax<M extends GameMove, R extends GameRole> extends EvaluatingGameAI<M, R> {
	private final MoveEvaluator<M, R> evaluator;
	private final MovePruner<M, R> pruner;
	private int depth = 0;
	
	/**
	 * Creates a new Minimax that attempts to
	 * search the entire game tree and analyze
	 * based off winners. This is only suitable
	 * for very simple games like Tic-Tac-Toe.
	 */
	public Minimax() {
		this(new WinEvaluator<>(), Integer.MAX_VALUE);
	}
	
	public Minimax(MoveEvaluator<M, R> evaluator, int depth) {
		this.evaluator = evaluator;
		this.depth = depth;
		pruner = new NeverPruner<>();
	}
	
	public Minimax(MoveEvaluator<M, R> evaluator, MovePruner<M, R> pruner, int depth) {
		this.evaluator = evaluator;
		this.pruner = pruner;
		this.depth = depth;
	}

	@Override
	protected double rateMove(GameState<M, R> gameBeforeMove, M move, Timer timer) {
		if (!gameBeforeMove.getCurrentRole().hasOpponent()) {
			throw new IllegalStateException("Minimax can only operate on two-player games!");
		}
		
		return minimax(gameBeforeMove.getCurrentRole(), gameBeforeMove, move, depth, timer);
	}
	
	private double minimax(
			R role,
			GameState<M, R> gameBeforeMove,
			M move,
			int decrementalDepth,
			Timer timer
	) {
		GameState<M, R> gameAfterMove = gameBeforeMove.spawnChild(move);
		
		if (!timer.isRunning()
				|| decrementalDepth == 0
				|| pruner.pruneMove(role, gameBeforeMove, gameAfterMove, move, depth - decrementalDepth)
				|| gameAfterMove.isGameOver()) {
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

	@Override
	public void setLevel(int depth) {
		this.depth = depth;
	}

	@Override
	public int getLevel() {
		return depth;
	}
}
