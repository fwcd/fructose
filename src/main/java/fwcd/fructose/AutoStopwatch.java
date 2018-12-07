package fwcd.fructose;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * A stopwatch that can automatically measure
 * the time for multiple inputs on an algorithm.
 * 
 * @author Fredrik
 *
 * @param <T>
 */
public class AutoStopwatch<T> {
	private T[] values;
	private Consumer<T> algorithm;
	
	@SafeVarargs
	public AutoStopwatch(Consumer<T> algorithm, T... values) {
		this.values = values;
		this.algorithm = algorithm;
	}
	
	public double[] get() {
		double[] results = new double[values.length];
		
		int i = 0;
		for (T input : values) {
			long startTime = System.nanoTime();
			
			algorithm.accept(input);
			
			results[i] = (System.nanoTime() - startTime) / 1000000D;
			i++;
		}
		
		return results;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(get()) + " ms";
	}
}
