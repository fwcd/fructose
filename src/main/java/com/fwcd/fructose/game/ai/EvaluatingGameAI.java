package com.fwcd.fructose.game.ai;

import java.util.NoSuchElementException;

import com.fwcd.fructose.game.GameMove;
import com.fwcd.fructose.game.GameRole;
import com.fwcd.fructose.game.GameState;
import com.fwcd.fructose.time.Timer;

/**
 * An evaluation-based game AI that can parallelize the
 * first layer in the game tree.
 * 
 * @author Fredrik
 *
 */
public abstract class EvaluatingGameAI extends TemplateGameAI {
	private boolean shouldParallelize = true;
	
	protected void setParallelization(boolean enabled) {
		shouldParallelize = enabled;
	}
	
	@Override
	protected <M extends GameMove, R extends GameRole> M selectMove(GameState<M, R> game, long softMaxTime) {
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
	
	protected abstract <M extends GameMove, R extends GameRole> double rateMove(GameState<M, R> gameBeforeMove, M move, Timer timer);
}
