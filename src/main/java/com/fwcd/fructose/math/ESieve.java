package com.fwcd.fructose.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Sieve of Eratosthenes (generating primes).
 * 
 * @author Fredrik
 *
 */
public class ESieve implements Iterable<Integer> {
	private List<Integer> primes = new ArrayList<>();
	
	/**
	 * Constructs a prime sieve using the algorithm from
	 * https://de.wikipedia.org/wiki/Sieb_des_Eratosthenes
	 * 
	 * @param bound
	 */
	public ESieve(int bound) {
		Map<Integer, Boolean> sieve = new HashMap<>();
		
		for (int i=2; i<=bound; i++) {
			sieve.put(i, true);
		}
		
		for (int i=2; i<=Math.sqrt(bound); i++) {
			if (sieve.get(i)) {
				primes.add(i);
				
				for (int j=i*i; j<=bound; j += i) {
					sieve.put(j, false);
				}
			}
		}
		
		for (int i=(int) Math.sqrt(bound)+1; i<=bound; i++) {
			if (sieve.get(i)) {
				primes.add(i);
			}
		}
	}
	
	public int get(int index) {
		return primes.get(index);
	}
	
	/**
	 * Checks if the given number is a prime contained in this
	 * sieve.
	 * 
	 * @param number - The number to be tested
	 * @return If the sieved primes contain this number
	 */
	public boolean contains(int number) {
		return primes.contains(number);
	}
	
	public List<Integer> asList() {
		return primes;
	}
	
	public List<Integer> asReversedList() {
		List<Integer> reversed = new ArrayList<>(primes);
		Collections.reverse(reversed);
		
		return reversed;
	}
	
	@Override
	public String toString() {
		return primes.toString();
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return primes.iterator();
	}
}
