package fwcd.fructose.game.ai;

import java.util.NoSuchElementException;

import fwcd.fructose.game.GameMove;
import fwcd.fructose.game.GameRole;
import fwcd.fructose.game.GameState;
import fwcd.fructose.time.Timer;

/**
 * An evaluation-based game AI that can parallelize the
 * first layer in the game tree.
 * 
 * @author Fredrik
 *
 */
public abstract class EvaluatingGameAI<M extends GameMove, R extends GameRole> extends TemplateGameAI<M, R> {
	private boolean shouldParallelize = true;
	
	protected void setParallelization(boolean enabled) {
		shouldParallelize = enabled;
	}
	
	@Override
	protected M selectMove(GameState<M, R> game, long softMaxTime) {
		final Timer timer = new Timer();
		timer.start(softMaxTime);
		
		if (shouldParallelize) {
			return game.getLegalMoves()
					.parallelStream()
					.max((a, b) -> Double.compare(rateMove(game, a, timer), rateMove(game, b, timer)))
					.orElseThrow(NoSuchElementException::new);
		} else {
			M bestMove = null;
			double bestRating = Double.NEGATIVE_INFINITY;
			
			for (M move : game.getLegalMoves()) {
				double rating = rateMove(game, move, timer);
				
				if (rating > bestRating) {
					bestRating = rating;
					bestMove = move;
				}
			}
			
			if (bestMove != null) {
				return bestMove;
			} else {
				throw new NoSuchElementException("Game state does not contain any moves.");
			}
		}
	}
	
	protected abstract double rateMove(GameState<M, R> gameBeforeMove, M move, Timer timer);
}
