package com.fwcd.fructose;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A non-null value that could either be
 * of the left or the right type.
 */
public class Either<L, R> {
	private final L left;
	private final R right;

	private Either(L left, R right) {
		this.left = left;
		this.right = right;
		assertNotCompletelyNull();
	}

	public Optional<L> getLeft() {
		return Optional.ofNullable(left);
	}

	public Optional<R> getRight() {
		return Optional.ofNullable(right);
	}

	public static <L, R> Either<L, R> ofLeft(L left) {
		return new Either<>(left, null);
	}

	public static <L, R> Either<L, R> ofRight(R right) {
		return new Either<>(null, right);
	}

	private void assertNotCompletelyNull() {
		if (left == null && right == null) {
			throw new IllegalStateException("Either can't hold two null values!");
		}
	}

	public L expectLeft() {
		if (left == null) {
			throw new NoSuchElementException("Either is not of the left type!");
		}
		return left;
	}

	public R expectRight() {
		if (right == null) {
			throw new NoSuchElementException("Either is not of the right type!");
		}
		return right;
	}

	public boolean isLeft() { return left != null; }

	public boolean isRight() { return right != null; }

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
		return "Either (" + get().toString() + ")";
	}
}
