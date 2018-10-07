package com.fwcd.fructose;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.LongStream;

import com.fwcd.fructose.operations.ToleranceEquatable;

/**
 * An immutable container that may or may not hold a {@code long}.
 * 
 * <p>{@link OptionLong} is a primitive specialization of {@link Option}.</p>
 * 
 * <p>This class closely follows the public interface
 * of {@link java.util.OptionalLong}.</p>
 * 
 * <p>Unlike {@link java.util.OptionalLong} which is only designed
 * to support the optional return idiom, {@link OptionLong} is
 * intended to be used as a general-purpose "Maybe"-type that
 * may be stored and serialized too.</p>
 * 
 * <p>Additionally, {@link OptionLong} is not marked as {@code final}, which
 * allows you to create subclasses (although it should be rarely
 * needed).</p>
 */
public class OptionLong implements Serializable, Iterable<Long>, ToleranceEquatable<OptionLong> {
	private static final long serialVersionUID = 4773007426244115874L;
	private static final OptionLong EMPTY = new OptionLong(0, false);
	private final long value;
	private final boolean present;
	
	/**
	 * Internally constructs a new {@link OptionLong} instance.
	 *  
	 * @param present - Whether the value is present
	 */
	private OptionLong(long value, boolean present) {
		this.value = value;
		this.present = present;
	}
	
	/**
	 * Creates a new {@link OptionLong} instance.
	 * 
	 * @return An {@link OptionLong} wrapping this value
	 */
	public static OptionLong of(long value) {
		return new OptionLong(value, true);
	}
	
	/**
	 * Converts a {@link java.util.OptionalLong} to an {@link OptionLong}.
	 */
	public static OptionLong of(OptionalLong value) {
		return value.isPresent() ? of(value.orElse(0)) : empty();
	}
	
	/**
	 * Fetches an empty/absent {@link OptionLong} instance.
	 */
	public static OptionLong empty() {
		return EMPTY;
	}
	
	/**
	 * Checks whether this value is present.
	 */
	public boolean isPresent() {
		return present;
	}
	
	/**
	 * Invokes the consumer if the value is present.
	 */
	public void ifPresent(LongConsumer then) {
		if (present) {
			then.accept(value);
		}
	}
	
	/**
	 * Invokes the first argument if the value is present,
	 * otherwise runs the second argument.
	 */
	public void ifPresentOrElse(LongConsumer then, Runnable otherwise) {
		if (present) {
			then.accept(value);
		} else {
			otherwise.run();
		}
	}
	
	/**
	 * Unwraps the value and throws an exception if absent.
	 * 
	 * <p>Generally, you should avoid this method
	 * and use {@code expect} or {@code orElse} instead.</p>
	 * 
	 * @throws NoSuchElementException if absent
	 * @return The wrapped value
	 */
	public long unwrap() {
		if (!present) {
			throw new NoSuchElementException("Tried to unwrap an empty OptionLong");
		}
		
		return value;
	}
	
	/**
	 * Unwraps the value and throws a message if absent.
	 * 
	 * @param messageIfAbsent - The error message
	 * @throws NoSuchElementException if absent
	 * @return The wrapped value
	 */
	public long unwrap(String messageIfAbsent) {
		if (!present) {
			throw new NoSuchElementException(messageIfAbsent);
		}
		
		return value;
	}
	
	/**
	 * Returns the wrapped value in case the value is present
	 * and matches the predicate, otherwise returns an empty
	 * {@link OptionLong}.
	 */
	public OptionLong filter(LongPredicate predicate) {
		Objects.requireNonNull(predicate, "Predicate can not be null");
		if (present) {
			return predicate.test(value) ? this : empty();
		} else {
			return this;
		}
	}
	
	/**
	 * Returns an {@link OptionLong} containing the result of the
	 * function if present, otherwise returns an empty {@link OptionLong}.
	 */
	public OptionLong map(LongUnaryOperator mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return of(mapper.applyAsLong(value));
		} else {
			return empty();
		}
	}
	
	/**
	 * Returns an {@link OptionInt} containing the result of the
	 * function if present, otherwise returns an empty {@link OptionInt}.
	 */
	public OptionInt mapToInt(LongToIntFunction mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return OptionInt.of(mapper.applyAsInt(value));
		} else {
			return OptionInt.empty();
		}
	}
	
	/**
	 * Returns an {@link OptionDouble} containing the result of the
	 * function if present, otherwise returns an empty {@link OptionDouble}.
	 */
	public OptionDouble mapToDouble(LongToDoubleFunction mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return OptionDouble.of(mapper.applyAsDouble(value));
		} else {
			return OptionDouble.empty();
		}
	}
	
	/**
	 * Returns an {@link OptionLong} containing the result of the
	 * function if present, otherwise returns an empty {@link OptionLong}.
	 */
	public <R> Option<R> mapToObj(LongFunction<? extends R> mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return Option.of(mapper.apply(value));
		} else {
			return Option.empty();
		}
	}
	
	/**
	 * Returns the result of the function if present,
	 * otherwise returns an empty {@link OptionLong}.
	 */
	public OptionLong flatMap(LongFunction<? extends OptionLong> mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return mapper.apply(value);
		} else {
			return empty();
		}
	}
	
	/**
	 * Returns the result of the function if present,
	 * otherwise returns an empty {@link OptionInt}.
	 */
	public OptionInt flatMapToInt(LongFunction<? extends OptionInt> mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return mapper.apply(value);
		} else {
			return OptionInt.empty();
		}
	}
	
	/**
	 * Returns the result of the function if present,
	 * otherwise returns an empty {@link OptionDouble}.
	 */
	public OptionDouble flatMapToDouble(LongFunction<? extends OptionDouble> mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return mapper.apply(value);
		} else {
			return OptionDouble.empty();
		}
	}
	
	/**
	 * Returns the result of the function if present,
	 * otherwise returns an empty {@link Option}.
	 */
	public <R> Option<R> flatMapToObj(LongFunction<? extends Option<R>> mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return mapper.apply(value);
		} else {
			return Option.empty();
		}
	}
	
	public OptionLong or(Supplier<OptionLong> other) {
		Objects.requireNonNull(other);
		return present ? this : Objects.requireNonNull(other.get());
	}
	
	/**
	 * Returns this value if present, otherwise the parameter.
	 */
	public long orElse(long other) {
		return present ? value : other;
	}
	
	/**
	 * Returns this value if present, otherwise the evaluated parameter.
	 */
	public long orElseGet(LongSupplier other) {
		return present ? value : other.getAsLong();
	}
	
	public <E extends Throwable> long orElseThrow(Supplier<? extends E> exception) throws E {
		if (present) {
			return value;
		} else {
			throw exception.get();
		}
	}
	
	/**
	 * Converts this {@link OptionLong} to a boxed nullable value.
	 */
	public Long orElseNull() { return present ? value : null; }
	
	/**
	 * Converts this {@link OptionLong} to a {@link java.util.OptionalLong}.
	 */
	public OptionalLong toOptionalLong() { return present ? OptionalLong.of(value) : OptionalLong.empty(); }
	
	public Option<Long> boxed() { return present ? Option.of(value) : Option.empty(); }
	
	public OptionInt toOptionInt() { return present ? OptionInt.of((int) value) : OptionInt.empty(); }
	
	public OptionDouble toOptionDouble() { return present ? OptionDouble.of(value) : OptionDouble.empty(); }
	
	@Override
	public Iterator<Long> iterator() {
		return new Iterator<Long>() {
			private boolean done = false;
			
			@Override
			public boolean hasNext() { return done; }
			
			@Override
			public Long next() {
				if (done) {
					throw new IllegalStateException("Tried to fetch more than one value from OptionLong.Iterator");
				} else {
					done = true;
					return value;
				}
			}
		};
	}
	
	public LongStream stream() {
		return present ? LongStream.of(value) : LongStream.empty();
	}
	
	@Override
	public String toString() {
		if (present) {
			return "OptionLong(" + value + ")";
		} else {
			return "OptionLong.empty";
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof OptionLong)) {
			return false;
		}
		OptionLong other = (OptionLong) obj;
		return (present == other.present) && (value == other.value);
	}
	
	@Override
	public int hashCode() {
		return present ? Long.hashCode(value) : 0;
	}

	@Override
	public boolean equals(OptionLong rhs, double tolerance) {
		if (rhs.isPresent()) {
			return Math.abs(rhs.value - value) <= tolerance;
		}
		return false;
	}
}
