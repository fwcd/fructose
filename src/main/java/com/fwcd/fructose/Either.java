package com.fwcd.fructose;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A non-null value that could either be
 * of the left or the right type.
 */
public class Either<L, R> implements Serializable {
	private static final long serialVersionUID = 6733622348610306228L;
	private final L left;
	private final R right;

	private Either(L left, R right) {
		this.left = left;
		this.right = right;
		assertNotCompletelyNull();
	}

	public Option<L> getLeft() {
		return Option.ofNullable(left);
	}

	public Option<R> getRight() {
		return Option.ofNullable(right);
	}

	public static <X, Y> Either<X, Y> ofLeft(X left) {
		Objects.requireNonNull(left, "Either.ofLeft can not be null");
		return new Either<>(left, null);
	}

	public static <X, Y> Either<X, Y> ofRight(Y right) {
		Objects.requireNonNull(right, "Either.ofRight can not be null");
		return new Either<>(null, right);
	}

	private void assertNotCompletelyNull() {
		if (left == null && right == null) {
			throw new IllegalStateException("Either can't hold two null values!");
		}
	}

	public L unwrapLeft() {
		if (left == null) {
			throw new NoSuchElementException("Either is not of the left type!");
		}
		return left;
	}

	public R unwrapRight() {
		if (right == null) {
			throw new NoSuchElementException("Either is not of the right type!");
		}
		return right;
	}
	
	/**
	 * @deprecated Use {@link unwrapLeft}.
	 */
	@Deprecated
	public L expectLeft() { return unwrapLeft(); }
	
	/**
	 * @deprecated Use {@link unwrapRight}.
	 */
	@Deprecated
	public R expectRight() { return unwrapRight(); }

	public boolean isLeft() { return left != null; }

	public boolean isRight() { return right != null; }
	
	public <X> X reduce(Function<? super L, ? extends X> leftMapper, Function<? super R, ? extends X> rightMapper) {
		if (isLeft()) {
			return leftMapper.apply(left);
		} else if (isRight()) {
			return rightMapper.apply(right);
		} else {
			throw new IllegalStateException("Either can't hold two null values!");
		}
	}
	
	public <X, Y> Either<X, Y> map(Function<? super L, ? extends X> leftMapper, Function<? super R, ? extends Y> rightMapper) {
		if (isLeft()) {
			return ofLeft(leftMapper.apply(left));
		} else if (isRight()) {
			return ofRight(rightMapper.apply(right));
		} else {
			throw new IllegalStateException("Either can't hold two null values!");
		}
	}
	
	public void match(Consumer<? super L> whenLeft, Consumer<? super R> whenRight) {
		if (isLeft()) {
			whenLeft.accept(left);
		} else if (isRight()) {
			whenRight.accept(right);
		} else {
			throw new IllegalStateException("Either can't hold two null values!");
		}
	}

	public Object get() {
		if (isLeft()) {
			return left;
		} else if (isRight()) {
			return right;
		} else {
			throw new IllegalStateException("Either can't hold two null values!");
		}
	}

	@Override
	public int hashCode() {
		return get().hashCode();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		return get().equals(((Either<L, R>) obj).equals(obj));
	}

	@Override
	public String toString() {
		return "Either(" + get().toString() + ")";
	}
}
