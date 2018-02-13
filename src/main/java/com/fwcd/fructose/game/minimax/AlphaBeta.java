package com.fwcd.fructose.game.minimax;

import com.fwcd.fructose.game.EvaluatingGameAI;
import com.fwcd.fructose.game.GameMove;
import com.fwcd.fructose.game.GameRole;
import com.fwcd.fructose.game.GameState;
import com.fwcd.fructose.game.MoveEvaluator;
import com.fwcd.fructose.game.WinEvaluator;
import com.fwcd.fructose.time.Timer;

/**
 * The alpha-beta tree search, which serves as an
 * optimization of the minimax algorithm.
 * 
 * @author Fredrik
 *
 */
public class AlphaBeta extends EvaluatingGameAI {
	private MoveEvaluator evaluator;
	private int depth = 0;
	
	/**
	 * Creates a new Minimax that attempts to
	 * search the entire game tree and analyze
	 * based off winners. This is only suitable
	 * for very simple games like Tic-Tac-Toe.
	 */
	public AlphaBeta() {
		this(new WinEvaluator(), Integer.MAX_VALUE);
	}
	
	public AlphaBeta(MoveEvaluator evaluator, int depth) {
		this.evaluator = evaluator;
		this.depth = depth;
	}

	@Override
	protected <M extends GameMove, R extends GameRole> double rateMove(GameState<M, R> gameBeforeMove, M move, Timer timer) {
		if (!gameBeforeMove.getCurrentRole().hasOpponent()) {
			throw new IllegalStateException("Alpha beta can only operate on two-player games!");
		}
		
		return alphaBeta(gameBeforeMove.getCurrentRole(), gameBeforeMove, move, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, timer);
	}
	
	private <M extends GameMove, R extends GameRole> double alphaBeta(
			R role,
			GameState<M, R> gameBeforeMove,
			M move,
			int decrementalDepth,
			double alpha,
			double beta,
			Timer timer
	) {
		GameState<M, R> gameAfterMove = gameBeforeMove.spawnChild(move);
		
		if (!timer.isRunning() || decrementalDepth == 0 || gameAfterMove.isGameOver()) {
			return evaluator.rate(role, gameBeforeMove, gameAfterMove, move, depth - decrementalDepth);
		} else {
			boolean maximizing = gameAfterMove.getCurrentRole().equals(role);
			double bestRating = maximizing ? alpha : beta;
			
			for (M childMove : gameAfterMove.getLegalMoves()) {
				if (!timer.isRunning()) {
					break;
				}
				
				double rating;
				
				if (maximizing) {
					rating = alphaBeta(role, gameAfterMove, childMove, decrementalDepth - 1, bestRating, beta, timer);
					if (rating > bestRating) {
						bestRating = rating;
						if (bestRating >= beta) {
							break; // Beta-cutoff
						}
					}
				} else {
					rating = alphaBeta(role, gameAfterMove, childMove, decrementalDepth - 1, alpha, bestRating, timer);
					if (rating < bestRating) {
						bestRating = rating;
						if (bestRating <= alpha) {
							break; // Alpha-cutoff
						}
					}
				}
			}
			
			return bestRating;
		}
	}
}
