package fwcd.fructose.math.algebra;

import fwcd.fructose.operations.Addable;
import fwcd.fructose.operations.Multipliable;
import fwcd.fructose.operations.Subtractable;

/**
 * An element from a mathematical set on
 * which addition and multiplication is defined.
 */
public interface RingElement<V extends RingElement<V>> extends
		Addable<V, V>,
		Subtractable<V, V>,
		Multipliable<V, V> {}
