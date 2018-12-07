package fwcd.fructose;

import java.util.function.Consumer;

/**
 * Safely wraps a result that could either be a value
 * or an exception.
 */
public class Result<T, E> {
	private final Either<T, E> value;

	private Result(Either<T, E> value) {
		this.value = value;
	}

	public static <T, E> Result<T, E> of(T value) {
		return new Result<>(Either.ofLeft(value));
	}

	public static <T, E> Result<T, E> ofEither(Either<T, E> value) {
		return new Result<>(value);
	}
	
	public static <T, E> Result<T, E> ofFailure(E error) {
		return new Result<>(Either.ofRight(error));
	}
	
	public T unwrap() {
		if (value.isLeft()) {
			return value.unwrapLeft();
		} else {
			E failure = value.unwrapRight();
			if (failure instanceof Throwable) {
				throw new IllegalStateException("Invalidly expected a successful result", (Throwable) failure);
			} else {
				throw new IllegalStateException("Invalidly expected a successful result: " + failure);
			}
		}
	}
	
	public E unwrapFailure() {
		if (value.isRight()) {
			return value.unwrapRight();
		} else {
			throw new IllegalStateException("Invalidly expected a failed result: " + value.unwrapLeft());
		}
	}
	
	public Either<T, E> toEither() { return value; }
	
	public void match(Consumer<T> whenSuccess, Consumer<E> whenFailure) { value.match(whenSuccess, whenFailure); }

	public boolean isSuccess() { return value.isLeft(); }

	public boolean isFailure() { return value.isRight(); }

	public Option<T> get() { return value.getLeft(); }

	public Option<E> getFailure() { return value.getRight(); }
	
	/**
	 * @deprecated Use {@code unwrapFailure}
	 */
	@Deprecated
	public E unwrapError() { return unwrapFailure(); }
	
	/**
	 * @deprecated Use {@code getFailure}
	 */
	@Deprecated
	public Option<E> getError() { return getFailure(); }
}
