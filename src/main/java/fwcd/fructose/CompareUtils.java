package fwcd.fructose;

public final class CompareUtils {
	private CompareUtils() {}
	
	@SafeVarargs
	public static <C extends Comparable<? super C>> C max(C a, C... others) {
		C result = a;
		for (C other : others) {
			if (other.compareTo(result) > 0) {
				result = other;
			}
		}
		return result;
	}
	
	@SafeVarargs
	public static <C extends Comparable<? super C>> C min(C a, C... others) {
		C result = a;
		for (C other : others) {
			if (other.compareTo(result) < 0) {
				result = other;
			}
		}
		return result;
	}
}
