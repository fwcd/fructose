package fwcd.fructose.game.ai;

import fwcd.fructose.game.GameMove;
import fwcd.fructose.game.GameRole;
import fwcd.fructose.game.GameState;
import fwcd.fructose.game.MoveEvaluator;
import fwcd.fructose.game.MovePruner;
import fwcd.fructose.game.NeverPruner;
import fwcd.fructose.game.WinEvaluator;
import fwcd.fructose.time.Timer;

/**
 * The alpha-beta tree search, which serves as an
 * optimization of the minimax algorithm.
 * 
 * @author Fredrik
 *
 */
public class AlphaBeta<M extends GameMove, R extends GameRole> extends EvaluatingGameAI<M, R> {
	private final MoveEvaluator<M, R> evaluator;
	private final MovePruner<M, R> pruner;
	private int depth = 0;
	
	/**
	 * Creates a new Minimax that attempts to
	 * search the entire game tree and analyze
	 * based off winners. This is only suitable
	 * for very simple games like Tic-Tac-Toe.
	 */
	public AlphaBeta() {
		this(new WinEvaluator<>(), Integer.MAX_VALUE);
	}
	
	public AlphaBeta(MoveEvaluator<M, R> evaluator, int depth) {
		this.evaluator = evaluator;
		this.depth = depth;
		pruner = new NeverPruner<>();
	}
	
	public AlphaBeta(MoveEvaluator<M, R> evaluator, MovePruner<M, R> pruner, int depth) {
		this.evaluator = evaluator;
		this.depth = depth;
		this.pruner = pruner;
	}

	@Override
	protected double rateMove(GameState<M, R> gameBeforeMove, M move, Timer timer) {
		if (!gameBeforeMove.getCurrentRole().hasOpponent()) {
			throw new IllegalStateException("Alpha beta can only operate on two-player games!");
		}
		
		return alphaBeta(gameBeforeMove.getCurrentRole(), gameBeforeMove, move, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, timer);
	}
	
	private double alphaBeta(
			R role,
			GameState<M, R> gameBeforeMove,
			M move,
			int decrementalDepth,
			double alpha,
			double beta,
			Timer timer
	) {
		GameState<M, R> gameAfterMove = gameBeforeMove.spawnChild(move);
		
		if (!timer.isRunning()
				|| decrementalDepth == 0
				|| pruner.pruneMove(role, gameBeforeMove, gameAfterMove, move, depth - decrementalDepth)
				|| gameAfterMove.isGameOver()) {
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

	@Override
	public void setLevel(int depth) {
		this.depth = depth;
	}

	@Override
	public int getLevel() {
		return depth;
	}
}
