package com.fredrikw.fructose.game.minimax;

import com.fredrikw.fructose.game.GameMove;
import com.fredrikw.fructose.game.GameRole;
import com.fredrikw.fructose.game.GameState;
import com.fredrikw.fructose.game.MoveEvaluator;
import com.fredrikw.fructose.game.TemplateGameAI;
import com.fredrikw.fructose.game.WinEvaluator;
import com.fredrikw.fructose.time.Timer;

/**
 * The alpha-beta tree search, which serves as an
 * optimization of the minimax algorithm.
 * 
 * @author Fredrik
 *
 */
public class AlphaBeta extends TemplateGameAI {
	private MoveEvaluator evaluator;
	private int depth = 0;
	private boolean debugOutput = false;
	
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
	
	public void setDebugOutput(boolean enabled) {
		debugOutput = enabled;
	}
	
	@Override
	protected GameMove selectMove(GameState game, long softMaxTime) {
		if (!game.getCurrentRole().hasOpponent()) {
			throw new IllegalStateException("Alpha beta can only operate on two-player games!");
		}
		
		Timer timer = new Timer();
		timer.start(softMaxTime);
		
		double bestRating = Double.NEGATIVE_INFINITY;
		GameMove bestMove = null;
		
		for (GameMove move : game.getLegalMoves()) {
			double rating = alphaBeta(game.getCurrentRole(), game, move, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, timer);

			if (debugOutput) {
				System.out.println("First layer: " + move.toString() + " -> " + Double.toString(rating));
			}
			
			if (rating > bestRating) {
				bestRating = rating;
				bestMove = move;
			}
		}
		System.out.println();
		
		return bestMove;
	}
	
	private double alphaBeta(
			GameRole role,
			GameState gameBeforeMove,
			GameMove move,
			int decrementalDepth,
			double alpha,
			double beta,
			Timer timer
	) {
		GameState gameAfterMove = gameBeforeMove.spawnChild(move);
		
		if (!timer.isRunning() || decrementalDepth == 0 || gameAfterMove.isGameOver()) {
			return evaluator.rate(role, gameBeforeMove, gameAfterMove, move, depth - decrementalDepth);
		} else {
			boolean maximizing = gameAfterMove.getCurrentRole().equals(role);
			double bestRating = maximizing ? alpha : beta;
			
			for (GameMove childMove : gameAfterMove.getLegalMoves()) {
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
