package com.fredrikw.fructose.time;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

import com.fredrikw.fructose.exception.Rethrow;

public class Timer {
	private boolean resetted = true;
	private long endTime = 0;
	
	public void start(long durationMs) {
		endTime = System.currentTimeMillis() + durationMs;
		resetted = false;
	}
	
	public void schedule(Runnable task, long customDurationMs) {
		new Thread(() -> {
			try {
				Thread.sleep(customDurationMs);
				task.run();
			} catch (InterruptedException e) {
				throw new Rethrow(e);
			}
		}).start();
	}
	
	public void schedule(Runnable onEnd) {
		schedule(onEnd, getRemainingMillis());
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
