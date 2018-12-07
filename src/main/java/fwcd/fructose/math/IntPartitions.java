package fwcd.fructose.math;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Represents every possible partition of a given number
 * to a specified amount of detail (smallest segment, defaults to 1).
 * 
 * @author Fredrik
 *
 */
public class IntPartitions implements Iterable<List<Integer>> {
	private List<List<Integer>> partitions = new ArrayList<>();
	
	public IntPartitions(int number, IntFunction<Boolean> isSmallestPart) {
		generatePartitions(number, number, new ArrayList<>(), isSmallestPart);
	}
	
	public IntPartitions(int number) {
		this(number, n -> n == 1);
	}
	
	/**
	 * Partitions a given number using the algorithm from
	 * http://introcs.cs.princeton.edu/java/23recursion/Partition.java.html
	 * 
	 * @param number - The number to be partitioned
	 * @param max - The number to be partitioned (needed for recursion)
	 * @param current - An empty list (needed for recursion)
	 * @param isSmallestPart - A function determining from a given number if this is the smallest segment (default: (n) -> {return n == 1;})
	 */
	private void generatePartitions(int number, int max, List<Integer> current, IntFunction<Boolean> isSmallestPart) {
		if (number == 0) {
			partitions.add(current);
			return;
		}

        for (int i = Math.min(max, number); i >= 1; i--) {
        	List<Integer> next = new ArrayList<>(current);
        	next.add(i);
        	generatePartitions(number - i, i, next, isSmallestPart);
        	
        	if (isSmallestPart.apply(i)) {
        		break;
        	}
        }
	}
	
	public List<List<Integer>> asList() {
		return partitions;
	}
	
	@Override
	public Iterator<List<Integer>> iterator() {
		return partitions.iterator();
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for (List<Integer> list : this) {
			s += list.toString() + "\n";
		}
		
		return s;
	}
}
