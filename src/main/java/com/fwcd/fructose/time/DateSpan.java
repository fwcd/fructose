package com.fwcd.fructose.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * A span of time.
 * 
 * @author Fredrik
 *
 */
public class DateSpan {
	private final LocalDate start; // Inclusive
	private final LocalDate end; // Exclusive
	
	/**
	 * Constructs a new TimeSpan.
	 * 
	 * @param start - The start date (inclusive)
	 * @param end - The end date (exclusive)
	 */
	public DateSpan(LocalDate start, LocalDate end) {
		this.start = start;
		this.end = end;
	}
	
	public boolean contains(LocalDate date) {
		return (date.isAfter(start) || date.equals(start))
				&& date.isBefore(end);
	}
	
	public int lengthInDays() {
		return (int) ChronoUnit.DAYS.between(start, end);
	}
	
	public LocalDate getStart() {
		return start;
	}
	
	public LocalDate getEnd() {
		return end;
	}
	
	@Override
	public String toString() {
		return start.toString() + " -> " + end.toString();
	}
}
