package com.fwcd.fructose.test.unittest;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

import com.fwcd.fructose.time.LocalDateInterval;
import com.fwcd.fructose.time.LocalDateTimeInterval;
import com.fwcd.fructose.time.LocalTimeInterval;

import org.junit.Test;

public class LocalDateTimeIntervalsTest {
	@Test
	public void testLocalDateTimeInterval() {
		LocalDateTime dtA = LocalDateTime.of(2018, 1, 1, 8, 41);
		LocalDateTime dtB = LocalDateTime.of(2018, 1, 1, 9, 41);
		LocalDateTimeInterval a = new LocalDateTimeInterval(dtA, dtB);
		LocalDateTimeInterval b = new LocalDateTimeInterval(LocalDateTime.of(2017, 12, 3, 8, 41), LocalDateTime.of(2018, 1, 1, 9, 32));
		assertTrue(a.overlaps(b));
		assertTrue(a.contains(dtA));
		assertFalse(a.contains(dtB));
		assertThat(a.getStart().getDayOfMonth(), equalTo(1));
		assertThat(a.getEnd().getDayOfMonth(), equalTo(1));
		assertThat(a.getDuration(), equalTo(Duration.between(dtA, dtB)));
		assertThat(a.toLocalDateInterval(), equalTo(new LocalDateInterval(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 2))));
		assertThat(a.toLocalDateInterval(), not(equalTo(new LocalDateInterval(dtA.toLocalDate(), dtB.toLocalDate()))));
		assertThat(a.toLocalTimeInterval(), equalTo(new LocalTimeInterval(dtA.toLocalTime(), dtB.toLocalTime())));
	}
	
	@Test
	public void testLocalTimeInterval() {
		LocalTime timeA = LocalTime.of(14, 04);
		LocalTime timeB = LocalTime.of(18, 10);
		LocalTimeInterval a = new LocalTimeInterval(timeA, timeB);
		LocalTimeInterval b = new LocalTimeInterval(LocalTime.of(10, 02), timeA);
		LocalTimeInterval c = new LocalTimeInterval(LocalTime.of(10, 02), LocalTime.of(14, 15));
		assertFalse(a.overlaps(b));
		assertTrue(a.overlaps(c));
		assertTrue(a.contains(timeA));
		assertFalse(a.contains(timeB));
		assertThat(a.getStart(), equalTo(timeA));
		assertThat(a.getEnd(), equalTo(timeB));
		assertTrue(a.contains(LocalTime.of(14, 05)));
		assertFalse(a.contains(LocalTime.of(21, 03)));
	}
	
	@Test
	public void testLocalDateInterval() {
		LocalDate dateA = LocalDate.of(2018, 3, 4);
		LocalDate dateB = LocalDate.of(2018, 4, 1);
		LocalDateInterval a = new LocalDateInterval(dateA, dateB);
		LocalDateInterval b = new LocalDateInterval(dateB, LocalDate.of(2018, 12, 31));
		LocalDateInterval c = new LocalDateInterval(LocalDate.of(2018, 3, 21), LocalDate.of(2019, 1, 1));
		assertFalse(a.overlaps(b));
		assertTrue(a.overlaps(c));
		assertThat(a.getStart(), equalTo(dateA));
		assertThat(a.getEnd(), equalTo(dateB));
		assertThat(a.getPeriod(), equalTo(Period.between(dateA, dateB)));
		assertTrue(a.contains(dateA));
		assertFalse(a.contains(dateB));
	}
}
