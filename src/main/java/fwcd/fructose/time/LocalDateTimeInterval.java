package fwcd.fructose.time;

import java.time.Duration;
import java.time.LocalDateTime;

import fwcd.fructose.util.CompareUtils;

/**
 * A fixed, half-open interval between two {@link LocalDateTime}s.
 */
public class LocalDateTimeInterval {
	private final LocalDateTime startInclusive;
	private final LocalDateTime endExclusive;
	
	public LocalDateTimeInterval(LocalDateTime startInclusive, LocalDateTime endExclusive) {
		this.startInclusive = startInclusive;
		this.endExclusive = endExclusive;
	}
	
	/** Returns the inclusive start */
	public LocalDateTime getStart() { return startInclusive; }
	
	/** Returns the exclusive end */
	public LocalDateTime getEnd() { return endExclusive; }
	
	public Duration getDuration() { return Duration.between(startInclusive, endExclusive); }
	
	public boolean contains(LocalDateTime date) {
		return (date.compareTo(startInclusive) >= 0) && (date.compareTo(endExclusive) < 0);
	}
	
	public LocalDateTimeInterval merge(LocalDateTimeInterval other) {
		return new LocalDateTimeInterval(
			CompareUtils.min(getStart(), getEnd(), other.getStart(), other.getEnd()),
			CompareUtils.max(getStart(), getEnd(), other.getStart(), other.getEnd())
		);
	}
	
	public boolean overlaps(LocalDateTimeInterval other) {
		return getStart().isBefore(other.getEnd()) && other.getStart().isBefore(getEnd());
	}
	
	public LocalDateInterval toLocalDateInterval() {
		return new LocalDateInterval(startInclusive.toLocalDate(), endExclusive.toLocalDate().plusDays(1));
	}
	
	public LocalTimeInterval toLocalTimeInterval() {
		return new LocalTimeInterval(startInclusive.toLocalTime(), endExclusive.toLocalTime());
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
		LocalDateTimeInterval other = (LocalDateTimeInterval) obj;
		return startInclusive.equals(other.startInclusive)
			&& endExclusive.equals(other.endExclusive);
	}
	
	@Override
	public int hashCode() {
		return 9 * startInclusive.hashCode() * endExclusive.hashCode();
	}
}
