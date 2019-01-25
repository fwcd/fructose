package fwcd.fructose;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

import fwcd.fructose.operations.ToleranceEquatable;

/**
 * An immutable container that may or may not hold a {@code double}.
 * 
 * <p>{@link OptionDouble} is a primitive specialization of {@link Option}.</p>
 * 
 * <p>This class closely follows the public interface
 * of {@link java.util.OptionalDouble}.</p>
 * 
 * <p>Unlike {@link java.util.OptionalDouble} which is only designed
 * to support the optional return idiom, {@link OptionDouble} is
 * intended to be used as a general-purpose "Maybe"-type that
 * may be stored and serialized too.</p>
 * 
 * <p>Additionally, {@link OptionDouble} is not marked as {@code final}, which
 * allows you to create subclasses (although it should be rarely
 * needed).</p>
 */
public class OptionDouble implements Serializable, Iterable<Double>, ToleranceEquatable<OptionDouble> {
	private static final long serialVersionUID = 6882577681703699494L;
	private static final OptionDouble EMPTY = new OptionDouble(0, false);
	private final double value;
	private final boolean present;
	
	/**
	 * Internally constructs a new {@link OptionDouble} instance.
	 *  
	 * @param present - Whether the value is present
	 */
	private OptionDouble(double value, boolean present) {
		this.value = value;
		this.present = present;
	}
	
	/**
	 * Creates a new {@link OptionDouble} instance.
	 * 
	 * @return An {@link OptionDouble} wrapping this value
	 */
	public static OptionDouble of(double value) {
		return new OptionDouble(value, true);
	}
	
	/**
	 * Converts a {@link java.util.OptionalDouble} to an {@link OptionDouble}.
	 */
	public static OptionDouble of(OptionalDouble value) {
		return value.isPresent() ? of(value.orElse(0)) : empty();
	}
	
	/**
	 * Fetches an empty/absent {@link OptionDouble} instance.
	 */
	public static OptionDouble empty() {
		return EMPTY;
	}
	
	/**
	 * Checks whether this value is present.
	 */
	public boolean isPresent() {
		return present;
	}
	
	/**
	 * Invokes the consumer if the value is present
	 * and returns this {@link OptionDouble}.
	 * 
	 * <p>This method is analogous to {@code DoubleStream.peek}
	 * and is mainly intended for debugging of call chains.
	 * Unlike {@code DoubleStream.peek}, this method runs synchronously
	 * and can thus be used safely for other purposes too.
	 * If no return type is needed, {@code ifPresent} should be used
	 * instead.</p>
	 */
	public OptionDouble peek(DoubleConsumer action) {
		if (present) {
			action.accept(value);
		}
		return this;
	}
	
	/**
	 * Invokes the consumer if the value is present.
	 */
	public void ifPresent(DoubleConsumer then) {
		if (present) {
			then.accept(value);
		}
	}
	
	/**
	 * Invokes the first argument if the value is present,
	 * otherwise runs the second argument.
	 */
	public void ifPresentOrElse(DoubleConsumer then, Runnable otherwise) {
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
	public double unwrap() {
		if (!present) {
			throw new NoSuchElementException("Tried to unwrap an empty OptionDouble");
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
	public double unwrap(String messageIfAbsent) {
		if (!present) {
			throw new NoSuchElementException(messageIfAbsent);
		}
		
		return value;
	}
	
	/**
	 * Returns the wrapped value in case the value is present
	 * and matches the predicate, otherwise returns an empty
	 * {@link OptionDouble}.
	 */
	public OptionDouble filter(DoublePredicate predicate) {
		Objects.requireNonNull(predicate, "Predicate can not be null");
		if (present) {
			return predicate.test(value) ? this : empty();
		} else {
			return this;
		}
	}
	
	/**
	 * Returns an {@link OptionDouble} containing the result of the
	 * function if present, otherwise returns an empty {@link OptionDouble}.
	 */
	public OptionDouble map(DoubleUnaryOperator mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return of(mapper.applyAsDouble(value));
		} else {
			return empty();
		}
	}
	
	/**
	 * Returns an {@link OptionInt} containing the result of the
	 * function if present, otherwise returns an empty {@link OptionInt}.
	 */
	public OptionInt mapToInt(DoubleToIntFunction mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return OptionInt.of(mapper.applyAsInt(value));
		} else {
			return OptionInt.empty();
		}
	}
	
	/**
	 * Returns an {@link OptionLong} containing the result of the
	 * function if present, otherwise returns an empty {@link OptionLong}.
	 */
	public OptionLong mapToLong(DoubleToLongFunction mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return OptionLong.of(mapper.applyAsLong(value));
		} else {
			return OptionLong.empty();
		}
	}
	
	/**
	 * Returns an {@link OptionDouble} containing the result of the
	 * function if present, otherwise returns an empty {@link OptionDouble}.
	 */
	public <R> Option<R> mapToObj(DoubleFunction<? extends R> mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return Option.of(mapper.apply(value));
		} else {
			return Option.empty();
		}
	}
	
	/**
	 * Returns the result of the function if present,
	 * otherwise returns an empty {@link OptionDouble}.
	 */
	public OptionDouble flatMap(DoubleFunction<? extends OptionDouble> mapper) {
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
	public OptionInt flatMapToInt(DoubleFunction<? extends OptionInt> mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return mapper.apply(value);
		} else {
			return OptionInt.empty();
		}
	}
	
	/**
	 * Returns the result of the function if present,
	 * otherwise returns an empty {@link OptionLong}.
	 */
	public OptionLong flatMapToLong(DoubleFunction<? extends OptionLong> mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return mapper.apply(value);
		} else {
			return OptionLong.empty();
		}
	}
	
	/**
	 * Returns the result of the function if present,
	 * otherwise returns an empty {@link Option}.
	 */
	public <R> Option<R> flatMapToObj(DoubleFunction<? extends Option<R>> mapper) {
		Objects.requireNonNull(mapper, "Mapper function can not be null");
		if (present) {
			return mapper.apply(value);
		} else {
			return Option.empty();
		}
	}
	
	public OptionDouble or(Supplier<OptionDouble> other) {
		Objects.requireNonNull(other);
		return present ? this : Objects.requireNonNull(other.get());
	}
	
	/**
	 * Returns this value if present, otherwise the parameter.
	 */
	public double orElse(double other) {
		return present ? value : other;
	}
	
	/**
	 * Returns this value if present, otherwise the evaluated parameter.
	 */
	public double orElseGet(DoubleSupplier other) {
		return present ? value : other.getAsDouble();
	}
	
	public <E extends Throwable> double orElseThrow(Supplier<? extends E> exception) throws E {
		if (present) {
			return value;
		} else {
			throw exception.get();
		}
	}
	
	/**
	 * Converts this {@link OptionDouble} to a boxed nullable value.
	 */
	public Double orElseNull() { return present ? value : null; }
	
	/**
	 * Converts this {@link OptionDouble} to a {@link java.util.OptionalDouble}.
	 */
	public OptionalDouble toOptionalDouble() { return present ? OptionalDouble.of(value) : OptionalDouble.empty(); }
	
	public Option<Double> boxed() { return present ? Option.of(value) : Option.empty(); }
	
	public OptionInt toOptionInt() { return present ? OptionInt.of((int) value) : OptionInt.empty(); }
	
	public OptionLong toOptionLong() { return present ? OptionLong.of((long) value) : OptionLong.empty(); }
	
	public double[] toArray() { return present ? new double[] {value} : new double[0]; }
	
	@Override
	public Iterator<Double> iterator() {
		return present ? new SingleIterator<>(value) : Collections.emptyIterator();
	}
	
	public DoubleStream stream() {
		return present ? DoubleStream.of(value) : DoubleStream.empty();
	}
	
	@Override
	public String toString() {
		if (present) {
			return "OptionDouble(" + value + ")";
		} else {
			return "OptionDouble.empty";
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof OptionDouble)) {
			return false;
		}
		OptionDouble other = (OptionDouble) obj;
		return (present == other.present) && (value == other.value);
	}
	
	@Override
	public int hashCode() {
		return present ? Double.hashCode(value) : 0;
	}

	@Override
	public boolean equals(OptionDouble rhs, double tolerance) {
		if (rhs.isPresent()) {
			return Math.abs(rhs.value - value) <= tolerance;
		}
		return false;
	}
}
