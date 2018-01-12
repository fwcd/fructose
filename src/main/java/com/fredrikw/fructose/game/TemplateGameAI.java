package com.fredrikw.fructose.game;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.fredrikw.fructose.exception.Rethrow;

public abstract class TemplateGameAI implements GameAI {
	private long softMaxTime = Long.MAX_VALUE;
	private long hardMaxTime = Long.MAX_VALUE;
	
	private MoveChooser timeoutMoveChooser = new RandomMoveChooser();
	private long hardMaxBuffer = 50; // A small buffer time in which the timeoutMoveChooser is expected to run
	
	@Override
	public void setSoftMaxTime(long ms) {
		softMaxTime = ms;
	}

	@Override
	public void setHardMaxTime(long ms) {
		hardMaxTime = ms;
	}
	
	/**
	 * Sets the move chooser that should be used when
	 * the hard time limit has passed. This chooser is
	 * expected to return very quickly.<br><br>
	 * 
	 * Furthermore a maximum time is provided that <b>should never</b> be
	 * exceeded by the chooser in any usual environment.
	 * 
	 * @param chooser - The hard timeout move chooser
	 * @param maxMs - The MAXIMUM time in ms that this chooser will require
	 */
	public void setTimeoutMoveChooser(MoveChooser chooser, long maxMs) {
		timeoutMoveChooser = chooser;
		hardMaxBuffer = maxMs;
	}
	
	@Override
	public GameMove chooseMove(GameState game) {
		RunnableFuture<GameMove> result = new FutureTask<>(() -> selectMove(game, softMaxTime));
		GameMove move;
		
		try {
			result.run();
			move = result.get(hardMaxTime - hardMaxBuffer, TimeUnit.MILLISECONDS); // FIXME: Debug
		} catch (TimeoutException e) {
			move = timeoutMoveChooser.chooseMove(game);
		} catch (InterruptedException | ExecutionException e) {
			throw new Rethrow(e);
		}
		
		return move;
	}
	
	protected abstract GameMove selectMove(GameState game, long softMaxTime);
}
