package com.fredrikw.fructose.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

/**
 * An incrementing time measurement device.
 * 
 * @author Fredrik
 *
 */
public class Stopwatch {
	private boolean resetted = true;
	private long startTime = 0;
	private long delta = 0;
	
	/**
	 * (Re-)starts the stopwatch. Note that this method
	 * might take about 10 ms to run.
	 */
	public void start() {
		startTime = now();
		resetted = false;
	}
	
	/**
	 * Fetches the time.
	 * 
	 * @return The total time measured in ms.
	 */
	public long getMillis() {
		if (resetted) {
			return 0;
		} else {
			return now() - startTime + delta;
		}
	}
	
	public long get(ChronoUnit unit) {
		Temporal startTemp = Instant.ofEpochMilli(startTime);
		Temporal endTemp = Instant.ofEpochMilli(getMillis());
		
		return unit.between(startTemp, endTemp);
	}
	
	private void reset() {
		resetted = true;
		delta = 0;
	}
	
	/**
	 * Pauses the stopwatch.
	 * 
	 * @return The total time measured in ms
	 */
	public long pause() {
		long ms = getMillis();
		delta += ms;
		return ms;
	}
	
	/**
	 * Pauses and resets the stopwatch.
	 * 
	 * @return The total time measured in ms
	 */
	public long stop() {
		long ms = getMillis();
		reset();
		return ms;
	}

	private long now() {
		return System.currentTimeMillis();
	}
	
	@Override
	public String toString() {
		return getMillis() + " ms";
	}
}
