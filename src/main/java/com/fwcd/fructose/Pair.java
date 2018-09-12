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
 */
public class Pair<L, R> implements Serializable, Iterable<Either<L, R>> {
	private static final long serialVersionUID = 6344477728413315385L;
	private final L left;
	private final R right;

	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public Pair(Entry<L, R> entry) {
		left = entry.getKey();
		right = entry.getValue();
	}

	public static <L, R> Pair<L, R> of(L left, R right) {
		return new Pair<>(left, right);
	}

	public L getLeft() { return left; }

	public R getRight() { return right; }

	public <T> T reduce(BiFunction<? super L, ? super R, ? extends T> reducer) {
		return reducer.apply(left, right);
	}

	public <X, Y> Pair<X, Y> map(Function<? super L, ? extends X> leftMapper, Function<? super R, ? extends Y> rightMapper) {
		return new Pair<>(leftMapper.apply(left), rightMapper.apply(right));
	}

	public <X> Pair<X, R> mapLeft(Function<? super L, ? extends X> mapper) {
		return new Pair<>(mapper.apply(left), right);
	}

	public <Y> Pair<L, Y> mapRight(Function<? super R, ? extends Y> mapper) {
		return new Pair<>(left, mapper.apply(right));
	}

	public <X> Pair<X, R> withLeft(X left) {
		return new Pair<>(left, this.right);
	}

	public <Y> Pair<L, Y> withRight(Y right) {
		return new Pair<>(this.left, right);
	}

	@SuppressWarnings("unlikely-arg-type")
	public boolean areBothEqual() {
		return left.equals(right);
	}

	@Override
	public String toString() {
		return "Pair(" + left.toString() + ", " + right.toString() + ")";
	}

	public Stream<Either<L, R>> stream() {
		return Stream.of(Either.ofLeft(left), Either.ofRight(right));
	}

	public Map<L, R> asMap() {
		return Collections.singletonMap(left, right);
	}

	public List<Either<L, R>> asList() {
		return Arrays.asList(Either.ofLeft(left), Either.ofRight(right));
	}

	@Override
	public Iterator<Either<L, R>> iterator() {
		return new Iterator<Either<L, R>>() {
			private int i = 0;

			@Override
			public boolean hasNext() {
				return i < 2;
			}

			@Override
			public Either<L, R> next() {
				i++;

				switch (i) {
					case 1: return Either.ofLeft(left);
					case 2: return Either.ofRight(right);
					default: throw new RuntimeException("Wrong index.");
				}
			}
		};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

	@Deprecated
	public L getA() { return getLeft(); }

	@Deprecated
	public R getB() { return getRight(); }

	@Deprecated
	public <X> Pair<X, R> mapA(Function<? super L, X> mapper) { return mapLeft(mapper); }

	@Deprecated
	public <Y> Pair<L, Y> mapB(Function<? super R, Y> mapper) { return mapRight(mapper); }

	@Deprecated
	public <X> Pair<X, R> withA(X a) { return withLeft(a); }

	@Deprecated
	public <Y> Pair<L, Y> withB(Y b) { return withRight(b); }
}
