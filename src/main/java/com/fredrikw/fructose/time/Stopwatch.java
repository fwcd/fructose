package com.fredrikw.fructose.time;

import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * An incremental time-measurement device.
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
		startTime = System.currentTimeMillis();
		resetted = false;
	}
	
	/**
	 * Fetches the time.
	 * 
	 * @return The total time measured in ms.
	 */
	public long getMillis() {
		if (resetted) {
			throw new IllegalStateException("Stopwatch not running");
		} else {
			return System.currentTimeMillis() - startTime + delta;
		}
	}
	
	public long get(TemporalUnit unit) {
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
	
	@Override
	public String toString() {
		return getMillis() + " ms";
	}
}
