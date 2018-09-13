package com.fwcd.fructose;

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

	public static <T, E extends Throwable> Result<T, E> ofEither(Either<T, E> value) {
		return new Result<>(value);
	}
	
	public static <T, E extends Throwable> Result<T, E> ofFailure(E error) {
		return new Result<>(Either.ofRight(error));
	}
	
	public T unwrap() {
		if (value.isLeft()) {
			return value.unwrapLeft();
		} else {
			throw new IllegalStateException("Invalidly expected a successful result", value.unwrapRight());
		}
	}
	
	public E unwrapError() {
		if (value.isRight()) {
			return value.unwrapRight();
		} else {
			throw new IllegalStateException("Invalidly expected a failed result: " + value.unwrapLeft());
		}
	}

	public boolean isSuccess() {
		return value.isLeft();
	}

	public boolean isFailure() {
		return value.isRight();
	}

	public Option<T> get() {
		return value.getLeft();
	}

	public Option<E> getError() {
		return value.getRight();
	}
	
	/**
	 * @deprecated Use {@code unwrap}
	 */
	@Deprecated
	public T expect() { return unwrap(); }
	
	/**
	 * @deprecated Use {@code unwrapError}
	 */
	@Deprecated
	public E expectError() { return unwrapError(); }
}
