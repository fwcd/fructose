package fwcd.fructose.math;

import java.util.Iterator;

/**
 * Iterates over the (infinitely many) primes.
 * 
 * @author Fredrik
 *
 */
public class PrimeIterator implements Iterator<Integer>, Iterable<Integer> {
	private int current;
	
	public PrimeIterator() {
		current = 1;
	}
	
	public PrimeIterator(int start) {
		current = start;
	}
	
	@Override
	public boolean hasNext() {
		return true; // As there are infinitely many primes. :)
	}

	@Override
	public Integer next() {
		do {
			current++;
		} while (!ExtMath.isPrime(current));
		
		return current;
	}

	@Override
	public Iterator<Integer> iterator() {
		return this;
	}
}
