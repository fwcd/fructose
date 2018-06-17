package com.fwcd.fructose;

import java.util.Optional;

/**
 * Safely wraps a result that could either be a value
 * of an exception.
 */
public class Result<T, E extends Throwable> {
	private final Either<T, E> value;

	private Result(Either<T, E> value) {
		this.value = value;
	}

	public static <T, E extends Throwable> Result<T, E> of(T value) {
		return new Result<>(Either.ofLeft(value));
	}

	public static <T, E extends Throwable> Result<T, E> ofFailure(E error) {
		return new Result<>(Either.ofRight(error));
	}

	public Optional<T> get() {
		return value.getLeft();
	}

	public T expect() {
		if (value.isLeft()) {
			return value.expectLeft();
		} else {
			throw new IllegalStateException("Invalidly expected a successful result", value.expectRight());
		}
	}

	public boolean isSuccess() {
		return value.isLeft();
	}

	public boolean isFailure() {
		return value.isRight();
	}

	public Optional<E> getError() {
		return value.getRight();
	}

	public E expectError() {
		if (value.isLeft()) {
			return value.expectRight();
		} else {
			throw new IllegalStateException("Invalidly expected a failed result", value.expectRight());
		}
	}
}
