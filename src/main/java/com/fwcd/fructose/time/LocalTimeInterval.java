package com.fwcd.fructose.time;

import java.time.Duration;
import java.time.LocalTime;

/**
 * A fixed interval between two {@link LocalTime}s.
 */
public class LocalTimeInterval {
	private final LocalTime startInclusive;
	private final LocalTime endExclusive;
	
	public LocalTimeInterval(LocalTime startInclusive, LocalTime endExclusive) {
		this.startInclusive = startInclusive;
		this.endExclusive = endExclusive;
	}
	
	public LocalTime getStart() { return startInclusive; }
	
	public LocalTime getEnd() { return endExclusive; }
	
	public Duration getDuration() { return Duration.between(startInclusive, endExclusive); }
	
	public boolean contains(LocalTime date) {
		return (date.compareTo(startInclusive) >= 0) && (date.compareTo(endExclusive) < 0);
	}
	
	@Override
	public String toString() {
		return "[" + startInclusive + ", " + endExclusive + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!getClass().equals(obj.getClass())) return false;
		LocalTimeInterval other = (LocalTimeInterval) obj;
		return startInclusive.equals(other.startInclusive)
			&& endExclusive.equals(other.endExclusive);
	}
	
	@Override
	public int hashCode() {
		return 9 * startInclusive.hashCode() * endExclusive.hashCode();
	}
}
