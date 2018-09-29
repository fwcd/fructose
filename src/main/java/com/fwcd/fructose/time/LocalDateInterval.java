package com.fwcd.fructose.time;

import java.time.LocalDate;
import java.time.Period;

/**
 * A fixed interval between two {@link LocalDate}s.
 */
public class LocalDateInterval {
	private final LocalDate startInclusive;
	private final LocalDate endExclusive;
	
	public LocalDateInterval(LocalDate startInclusive, LocalDate endExclusive) {
		this.startInclusive = startInclusive;
		this.endExclusive = endExclusive;
	}
	
	public LocalDate getStart() { return startInclusive; }
	
	public LocalDate getEnd() { return endExclusive; }
	
	public Period getPeriod() { return Period.between(startInclusive, endExclusive); }
	
	public boolean contains(LocalDate date) {
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
		LocalDateInterval other = (LocalDateInterval) obj;
		return startInclusive.equals(other.startInclusive)
			&& endExclusive.equals(other.endExclusive);
	}
	
	@Override
	public int hashCode() {
		return 9 * startInclusive.hashCode() * endExclusive.hashCode();
	}
}
