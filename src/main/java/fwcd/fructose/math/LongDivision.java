package fwcd.fructose.math;

import java.util.HashMap;
import java.util.Map;

public class LongDivision {
	private int counter;
	private int denominator;
	
	public LongDivision(int counter, int denominator) {
		this.counter = counter;
		this.denominator = denominator;
	}
	
	public int periodicSequenceLength() {
		return periodicSequenceLength(counter, new HashMap<>(), true, 0);
	}
	
	private int periodicSequenceLength(int a, Map<Integer, Integer> foundAs, boolean isFirstRun, int index) {
		int digit = a / denominator;
		int b = digit * denominator;
		int c = a - b;
		
		// System.out.println("a: " + a + " - b: " + b + " - c: " + c + " - digit: " + digit);
		
		if (isFirstRun) {
			foundAs.put(a, index);
			
			return 1 + periodicSequenceLength(c * 10, foundAs, false, index + 1);
		} else if (a == 0) {
			throw new RuntimeException("Number is not periodic.");
		} else if (foundAs.containsKey(a)) {
			return -foundAs.get(Integer.valueOf(a));
		} else {
			foundAs.put(a, index);
			
			return 1 + periodicSequenceLength(c * 10, foundAs, false, index + 1);
		}
	}
}
