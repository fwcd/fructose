package com.fwcd.fructose.time;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

/**
 * A decremental time-measurement device. Can also be
 * used to schedule tasks.
 * 
 * @author Fredrik
 *
 */
public class Timer {
	private boolean resetted = true;
	private long endTime = 0;
	
	public void start(long durationMs) {
		long time = System.currentTimeMillis();
		endTime = time + durationMs;
		
		if (endTime < time) {
			// This will only happen if "endTime" is overflowed
			endTime = Long.MAX_VALUE;
		}
		
		resetted = false;
	}
	
	/**
	 * Waits for the timer to complete.
	 * 
	 * @throws InterruptedException - If this thread is interrupted
	 */
	public void waitFor() throws InterruptedException {
		Thread.sleep(getRemainingMillis());
	}
	
	public void reset() {
		resetted = true;
		endTime = 0;
	}
	
	public boolean isRunning() {
		return !resetted && System.currentTimeMillis() < endTime;
	}
	
	public long getRemainingMillis() {
		if (isRunning()) {
			return endTime - System.currentTimeMillis();
		} else {
			throw new IllegalStateException("Timer not running");
		}
	}
	
	public long getRemainingTime(TemporalUnit unit) {
		return Duration.ofMillis(getRemainingMillis()).get(unit);
	}
}
