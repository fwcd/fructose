package com.fredrikw.fructose;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * An immutable tuple consisting of two objects.
 * 
 * @author Fredrik W.
 *
 * @param <A>
 * @param <B>
 */
public class Pair<A, B> implements Iterable<Object> {
	private final A a;
	private final B b;
	
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public A getA() {
		return a;
	}
	
	public B getB() {
		return b;
	}

	public <T> Pair<T, B> withA(T a) {
		return new Pair<>(a, b);
	}
	
	public <T> Pair<A, T> withB(T b) {
		return new Pair<>(a, b);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public boolean areBothEqual() {
		return a.equals(b);
	}
	
	@Override
	public String toString() {
		return "<" + a.toString() + ", " + b.toString() + ">";
	}

	public Stream<Object> stream() {
		return Stream.of(a, b);
	}
	
	public List<Object> asList() {
		return Arrays.asList(a, b);
	}
	
	@Override
	public Iterator<Object> iterator() {
		return new Iterator<Object>() {
			private int i = 0;
			
			@Override
			public boolean hasNext() {
				return i < 2;
			}

			@Override
			public Object next() {
				i++;
				
				switch (i) {
				
				case 1:
					return a;
				case 2:
					return b;
				default:
					throw new RuntimeException("Wrong index.");
				
				}
			}
		};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair<?, ?> other = (Pair<?, ?>) obj;
		if (a == null) {
			if (other.a != null) {
				return false;
			}
		} else if (!a.equals(other.a)) {
			return false;
		}
		if (b == null) {
			if (other.b != null) {
				return false;
			}
		} else if (!b.equals(other.b)) {
			return false;
		}
		return true;
	}
}
