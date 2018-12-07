package fwcd.fructose.time;

import java.time.LocalDate;
import java.time.Period;

import fwcd.fructose.CompareUtils;

/**
 * A fixed, half-open interval between two {@link LocalDate}s.
 */
public class LocalDateInterval {
	private final LocalDate startInclusive;
	private final LocalDate endExclusive;
	
	public LocalDateInterval(LocalDate startInclusive, LocalDate endExclusive) {
		this.startInclusive = startInclusive;
		this.endExclusive = endExclusive;
	}
	
	/** Returns the inclusive start */
	public LocalDate getStart() { return startInclusive; }
	
	/** Returns the inclusive end */
	public LocalDate getLastDate() { return endExclusive.minusDays(1); }
	
	/** Returns the exclusive end */
	public LocalDate getEnd() { return endExclusive; }
	
	public Period getPeriod() { return Period.between(startInclusive, endExclusive); }
	
	public boolean contains(LocalDate date) {
		return (date.compareTo(startInclusive) >= 0) && (date.compareTo(endExclusive) < 0);
	}
	
	public LocalDateInterval merge(LocalDateInterval other) {
		return new LocalDateInterval(
			CompareUtils.min(getStart(), getEnd(), other.getStart(), other.getEnd()),
			CompareUtils.max(getStart(), getEnd(), other.getStart(), other.getEnd())
		);
	}
	
	public boolean overlaps(LocalDateInterval other) {
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
		LocalDateInterval other = (LocalDateInterval) obj;
		return startInclusive.equals(other.startInclusive)
			&& endExclusive.equals(other.endExclusive);
	}
	
	@Override
	public int hashCode() {
		return 9 * startInclusive.hashCode() * endExclusive.hashCode();
	}
}
