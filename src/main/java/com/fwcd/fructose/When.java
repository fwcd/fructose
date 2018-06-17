package com.fwcd.fructose;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * Syntactic sugar for if/elseif/switch/match/instanceof-chains.
 */
public class When {
	private boolean successfullyMatched = false;

	private When() {}

	public static When it() {
		return new When();
	}

	public When isTrue(boolean condition, Runnable then) {
		if (condition) {
			then.run();
			successfullyMatched = true;
		}
		return this;
	}

	public When elseWhenTrue(boolean condition, Runnable then) {
		if (!successfullyMatched && condition) {
			then.run();
		}
		return this;
	}

	public When elseWhenTrue(BooleanSupplier condition, Runnable then) {
		if (!successfullyMatched && condition.getAsBoolean()) {
			then.run();
		}
		return this;
	}

	public When isFalse(boolean condition, Runnable then) {
		if (condition) {
			then.run();
			successfullyMatched = true;
		}
		return this;
	}

	public void elseDo(Runnable then) {
		if (!successfullyMatched) {
			then.run();
		}
	}

	public static <T> WhenValue<T> value(T value) {
		return new WhenValue<>(value);
	}

	public static class WhenValue<T> {
		private final T value;
		private boolean successfullyMatched = false;

		private WhenValue(T value) {
			this.value = value;
		}

		public WhenValue<T> equals(T other, Runnable then) {
			return equals(other, v -> then.run());
		}

		public WhenValue<T> equals(T other, Consumer<T> then) {
			if (value.equals(other)) {
				then.accept(value);
				successfullyMatched = true;
			}
			return this;
		}

		public <C> WhenValue<T> is(Class<C> comparedClass, Runnable then) {
			return is(comparedClass, v -> then.run());
		}

		public <C> WhenValue<T> is(Class<C> comparedClass, Consumer<C> then) {
			if (value.getClass().equals(comparedClass)) {
				then.accept(comparedClass.cast(value));
				successfullyMatched = true;
			}
			return this;
		}

		public void elseUse(Consumer<T> then) {
			if (!successfullyMatched) {
				then.accept(value);
			}
		}

		public void elseDo(Runnable then) {
			if (!successfullyMatched) {
				then.run();
			}
		}
	}
}
