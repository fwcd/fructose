package com.fwcd.fructose;

public final class CompareUtils {
	private CompareUtils() {}
	
	public static <C extends Comparable<? super C>> C max(C a, C b) {
		return (a.compareTo(b) >= 0) ? a : b;
	}
	
	public static <C extends Comparable<? super C>> C min(C a, C b) {
		return (a.compareTo(b) <= 0) ? a : b;
	}
}
