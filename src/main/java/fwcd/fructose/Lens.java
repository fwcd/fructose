package fwcd.fructose;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A "view" on an inner part of an object.
 * Usually applied to (deeply) immutable
 * data structures.
 * 
 * @param <T> - The containing structure type
 * @param <E> - The entry type
 */
public class Lens<T, E> {
	private final Function<T, E> getter;
	private final BiFunction<T, E, T> setter;
	
	/**
	 * Creates a new lens. This can be done conveniently using
	 * method references:
	 * 
	 * <pre>new Lens(MyStructure::getValue, MyStructure::withValue)</pre>
	 * 
	 * (Assuming that the setter does not mutate the structure
	 * but instead creates a new one.)
	 * 
	 * @param getter - Fetches the value from the structure
	 * @param setter - Creates a new version of the structure with an updated value
	 */
	public Lens(Function<T, E> getter, BiFunction<T, E, T> setter) {
		this.getter = getter;
		this.setter = setter;
	}
	
	/**
	 * Fetches the value from the structure.
	 * 
	 * @param structure - The surrounding structure
	 * @return The (inner) value
	 */
	public E get(T structure) {
		return getter.apply(structure);
	}
	
	/**
	 * Creates a new version of the structure with
	 * an updated value.
	 * 
	 * @param structure - The surrounding structure
	 * @param newValue - The new (inner) value
	 */
	public T set(T structure, E newValue) {
		return setter.apply(structure, newValue);
	}
	
	/**
	 * Layers this lens below another one.
	 * 
	 * @param <U> - The outer structure
	 * @param outer - An outer lens focusing on this lenses structure
	 * @return An outer lens focusing on this lenses value
	 */
	public <U> Lens<U, E> below(Lens<U, T> outer) {
		return new Lens<>(
			getter.compose(outer.getter),
			(structure, newValue) -> outer.set(structure, set(outer.get(structure), newValue))
		);
	}
	
	/**
	 * Layers this lens above another one.
	 * 
	 * @param <U> - The outer structure
	 * @param inner - An outer lens focusing on this lenses structure
	 * @return An outer lens focusing on this lenses value
	 */
	public <F> Lens<T, F> compose(Lens<E, F> inner) {
		return new Lens<>(
			inner.getter.compose(getter),
			(structure, newValue) -> set(structure, inner.set(get(structure), newValue))
		);
	}
}
