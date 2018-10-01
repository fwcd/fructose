package com.fwcd.fructose.time;

import java.time.Duration;
import java.time.LocalTime;

import com.fwcd.fructose.CompareUtils;

/**
 * A fixed, half-open interval between two {@link LocalTime}s.
 */
public class LocalTimeInterval {
	private final LocalTime startInclusive;
	private final LocalTime endExclusive;
	
	public LocalTimeInterval(LocalTime startInclusive, LocalTime endExclusive) {
		this.startInclusive = startInclusive;
		this.endExclusive = endExclusive;
	}
	
	/** Returns the inclusive start */
	public LocalTime getStart() { return startInclusive; }
	
	/** Returns the exclusive end */
	public LocalTime getEnd() { return endExclusive; }
	
	public Duration getDuration() { return Duration.between(startInclusive, endExclusive); }
	
	public boolean contains(LocalTime date) {
		return (date.compareTo(startInclusive) >= 0) && (date.compareTo(endExclusive) < 0);
	}
	
	public LocalTimeInterval merge(LocalTimeInterval other) {
		return new LocalTimeInterval(
			CompareUtils.min(getStart(), getEnd(), other.getStart(), other.getEnd()),
			CompareUtils.max(getStart(), getEnd(), other.getStart(), other.getEnd())
		);
	}
	
	public boolean overlaps(LocalTimeInterval other) {
		return getStart().isBefore(other.getEnd()) && other.getStart().isBefore(getEnd());
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
