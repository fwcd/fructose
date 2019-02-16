package fwcd.fructose.util;

public class BoolUtils {
	private BoolUtils() {}
	
	public static boolean implies(boolean a, boolean b) {
		return !a || b;
	}
}
