package com.fwcd.fructose;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * An immutable tuple consisting of two objects.
 * 
 * @author Fredrik
 *
 * @param <A>
 * @param <B>
 */
public class Pair<A, B> implements Serializable, Iterable<Object> {
	private static final long serialVersionUID = 6344477728413315385L;
	private final A a;
	private final B b;
	
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public Pair(Entry<A, B> entry) {
		a = entry.getKey();
		b = entry.getValue();
	}
	
	public A getA() {
		return a;
	}
	
	public B getB() {
		return b;
	}
	
	public <T> T reduce(BiFunction<? super A, ? super B, T> reducer) {
		return reducer.apply(a, b);
	}
	
	public <X, Y> Pair<X, Y> map(Function<? super A, X> aMapper, Function<? super B, Y> bMapper) {
		return new Pair<>(aMapper.apply(a), bMapper.apply(b));
	}
	
	public <X> Pair<X, B> mapA(Function<? super A, X> mapper) {
		return new Pair<>(mapper.apply(a), b);
	}
	
	public <Y> Pair<A, Y> mapB(Function<? super B, Y> mapper) {
		return new Pair<>(a, mapper.apply(b));
	}
	
	public <X> Pair<X, B> withA(X a) {
		return new Pair<>(a, b);
	}
	
	public <Y> Pair<A, Y> withB(Y b) {
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
	
	public Map<A, B> asMap() {
		return Collections.singletonMap(a, b);
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
