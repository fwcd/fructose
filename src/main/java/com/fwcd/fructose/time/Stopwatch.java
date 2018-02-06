package com.fwcd.fructose.time;

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
	private boolean running = false;
	private long startTime = 0;
	private long delta = 0;
	
	/**
	 * (Re-)starts the stopwatch. Note that this method
	 * might take about 10 ms to run.
	 */
	public void start() {
		startTime = System.currentTimeMillis();
		resetted = false;
		running = true;
	}
	
	/**
	 * Fetches the time.
	 * 
	 * @return The total time measured in ms.
	 */
	public long getMillis() {
		if (!resetted) {
			return System.currentTimeMillis() - startTime + delta;
		} else {
			throw new IllegalStateException("Stopwatch not running");
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
		running = false;
	}
	
	/**
	 * Pauses the stopwatch.
	 * 
	 * @return The total time measured in ms
	 */
	public long pause() {
		long ms = getMillis();
		delta += ms;
		running = false;
		return ms;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Pauses and resets the stopwatch.
	 * 
	 * @return The total time measured in ms
	 */
	public long stop() {
		if (running) {
			long ms = getMillis();
			reset();
			running = false;
			return ms;
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return Long.toString(getMillis()) + " ms";
	}
}
