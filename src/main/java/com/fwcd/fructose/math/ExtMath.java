package com.fwcd.fructose.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntUnaryOperator;

import com.fwcd.fructose.ListUtils;
import com.fwcd.fructose.math.graph.ConstScalar;
import com.fwcd.fructose.math.graph.ScalarExponentiation;
import com.fwcd.fructose.structs.IntList;

/**
 * A set of utilities for performing custom, useful and common math operations.
 * (full name: ExtendedMath)
 * 
 * @author Fredrik
 *
 */
public final class ExtMath {
	private ExtMath() {}
	
	public static int ceilDivide(int counter, int denominator) {
		if (counter <= 0) {
			return (int) Math.ceil((double) counter / (double) denominator);
		} else {
			return (counter + denominator - 1) / denominator;
		}
	}
	
	public static int log2Floor(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
	    return 31 - Integer.numberOfLeadingZeros(n);
	}
	
	/**
	 * Calculates the square root of a BigInteger.<br>
	 * <br>
	 * 
	 * Code taken from
	 * https://gist.github.com/JochemKuijpers/cd1ad9ec23d6d90959c549de5892d6cb
	 * 
	 * @param n
	 * @return
	 */
	public static BigInteger sqrt(BigInteger n) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = n.shiftRight(5).add(BigInteger.valueOf(8));
		
		while (b.compareTo(a) >= 0) {
			BigInteger mid = a.add(b).shiftRight(1);
			if (mid.multiply(mid).compareTo(n) > 0) {
				b = mid.subtract(BigInteger.ONE);
			} else {
				a = mid.add(BigInteger.ONE);
			}
		}
		
		return a.subtract(BigInteger.ONE);
	}
	
	/**
	 * Calculates the square root of a BigInteger and throws an
	 * exception if the result is not a natural number.
	 * 
	 * @param n
	 * @return
	 */
	public static BigInteger exactSqrt(BigInteger n) {
		BigInteger sqrt = sqrt(n);
		
		if (sqrt.equals(n.pow(2))) {
			return sqrt;
		} else {
			throw new ArithmeticException("Square root is not a natural number.");
		}
	}

	/**
	 * Creates a sum, iterating over a range of integers and performing a specified
	 * operation on each value.
	 * 
	 * @param range
	 *            - Including both ends of the range
	 * @param operation
	 *            - Specified equation
	 * @return The sum
	 */
	public static int sigma(InclusiveIntRange range, IntUnaryOperator operation) {
		int result = 0;

		for (int n : range) {
			result += operation.applyAsInt(n);
		}

		return result;
	}

	public static List<ScalarExponentiation> expPrimeFactors(int number) {
		return summarizeFactors(primeFactors(number));
	}

	public static IntList primeFactors(long number) {
		IntList subFactors = new IntList();

		for (int i = 2; i < number; i++) {
			if (number % i == 0) {
				subFactors.addAll(primeFactors(number / i));
				subFactors.addAll(primeFactors(i));
				break;
			}
		}

		if (subFactors.isEmpty()) {
			subFactors.add((int) number);
		}

		return subFactors;
	}

	public static BigInteger binomCoefficient(int n, int k) {
		return binomCoefficient(BigInteger.valueOf(n), BigInteger.valueOf(k));
	}

	public static BigInteger binomCoefficient(BigInteger n, BigInteger k) {
		if (k.intValue() == 0) {
			return BigInteger.valueOf(1);
		} else {
			return n.divide(k)
					.multiply(binomCoefficient(n.subtract(BigInteger.valueOf(1)), k.subtract(BigInteger.valueOf(1))));
		}
	}
	
	public static BigInteger largeFactorial(BigInteger number) {
		BigInteger result = new BigInteger("1");

		for (BigInteger i = number; i.compareTo(BigInteger.ZERO) > 0; i = i.subtract(BigInteger.ONE)) {
			result = result.multiply(new BigInteger(i.toString()));
		}

		return result;
	}
	
	public static BigInteger largeFactorial(int number) {
		BigInteger result = new BigInteger("1");

		for (int i = number; i > 0; i--) {
			result = result.multiply(new BigInteger(Integer.toString(i)));
		}

		return result;
	}

	public static int factorial(int number) {
		int result = 1;

		for (int i = number; i > 0; i--) {
			try {
				Math.multiplyExact(result, i);
			} catch (ArithmeticException e) {
				throw new ArithmeticException("Factorial larger than Integer.MAX_VALUE. Try largeFactorial() instead.");
			}
			result *= i;
		}

		return result;
	}

	/**
	 * Naive and slow (but deterministic) prime tester.
	 * 
	 * @param x
	 *            - The number to be tested
	 * @return If it's a prime
	 */
	public static boolean isPrime(long x) {
		if (x <= 1) {
			return false;
		}

		for (long i = 2; i < x; i++) {
			if (x % i == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Fast, but probabilistic prime tester.
	 * 
	 * @param x
	 *            - The number to be test
	 * @return If it's probably a prime
	 */
	public static boolean fastIsPrime(long x, int certainty) {
		return BigInteger.valueOf(x).isProbablePrime(certainty);
	}

	public static BigInteger greatestCommonDivisor(BigInteger a, BigInteger b) {
		BigInteger modulo = a.mod(b);

		if (modulo.equals(BigInteger.ZERO)) {
			return b;
		} else {
			return greatestCommonDivisor(b, modulo);
		}
	}

	public static long greatestCommonDivisor(long a, long b) {
		long modulo = a % b;

		if (modulo == 0) {
			return b;
		} else {
			return greatestCommonDivisor(b, modulo);
		}
	}

	public static long leastCommonMultiple(long a, long b) {
		return a * (b / greatestCommonDivisor(a, b));
	}

	public static int sum(Iterable<Integer> values) {
		int sum = 0;

		for (int value : values) {
			sum += value;
		}

		return sum;
	}

	public static int[] getDigits(long number) {
		if (number == 0) {
			return new int[] { 0 };
		}

		number = Math.abs(number);

		int length = (int) (Math.log10(number) + 1);
		int[] digits = new int[length];

		for (int i = 0; i < length; i++) {
			digits[length - 1 - i] = (int) (number % 10);
			number /= 10;
		}

		return digits;

		/*
		 * String[] chars = Integer.toString(number).split(""); int[] digits = new
		 * int[chars.length];
		 * 
		 * for (int i=0; i<chars.length; i++) { digits[i] = Integer.parseInt(chars[i]);
		 * }
		 * 
		 * return digits;
		 */
	}

	public static List<Integer> digitList(long number) {
		return Arrays.asList(ListUtils.box(getDigits(number)));
	}

	public static LongFraction product(Iterable<LongFraction> fractions) {
		LongFraction p = new LongFraction(1, 1);

		for (LongFraction fraction : fractions) {
			p.multiply(fraction);
		}

		return p.reduce();
	}

	public static long fibonacci(long index) {
		if (index < 0) {
			throw new IllegalArgumentException();
		} else if (index == 0) {
			return 0;
		} else if (index == 1 || index == 2) {
			return 1;
		}
		
		long lastA = 0;
		long lastB = 1;
		
		for (int i=1; i<index; i++) {
			long next = Math.addExact(lastA, lastB);
			lastA = lastB;
			lastB = next;
		}
		
		return lastB;
	}
	
	/**
	 * Calculates euler's totient function.
	 * (Uses a prime check with a certainty of 15, which
	 * means that this isn't a deterministic algorithm)
	 * 
	 * @param number
	 * @return
	 */
	public static int phi(int number) {
		if (fastIsPrime(number, 15)) {
			return primePhi(number);
		}

		return sigma(new InclusiveIntRange(1, number), (x) -> {
			return greatestCommonDivisor(x, number) == 1 ? 1 : 0;
		});
	}

	/**
	 * Calculates euler's totient function given the prime factors of the number.
	 * NOTE that this function will NOT check if the factor's are valid primes (to
	 * enhance efficiency).
	 * 
	 * @param primeFactors
	 * @return
	 */
	public static int phi(IntList primeFactors) {
		int result = 1;

		for (int factor : primeFactors.toArray()) {
			result *= primePhi(factor);
		}

		return result;
	}

	/**
	 * Calculates euler's totient function though ONLY correctly if the number is a
	 * prime!!
	 * 
	 * @param number
	 * @return
	 */
	private static int primePhi(int number) {
		return number - 1;
	}

	private static ConstScalar term(double n) {
		return new ConstScalar(n);
	}
	
	public static List<ScalarExponentiation> summarizeFactors(IntList factors) {
		Collections.sort(factors.boxed());
		List<ScalarExponentiation> exponentiations = new ArrayList<>();

		int currentBase = 0;
		int currentExponent = 0;
		int i = 0;

		while (i < factors.size()) {
			if (currentBase != factors.get(i)) {
				if (i != 0) {
					exponentiations.add(new ScalarExponentiation(term(currentBase), term(currentExponent)));
				}

				currentBase = factors.get(i);
				currentExponent = 1;
			} else {
				currentExponent++;
			}

			i++;
		}

		exponentiations.add(new ScalarExponentiation(term(currentBase), term(currentExponent)));

		return exponentiations;
	}
	
	public static String binaryString(int v, int length) {
		String s = Integer.toBinaryString(v);
		while (s.length() < length) {
			s = "0" + s;
		}
		return s;
	}
}
