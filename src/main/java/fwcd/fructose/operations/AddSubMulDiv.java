package fwcd.fructose.operations;

import fwcd.fructose.operations.Addable;
import fwcd.fructose.operations.Divisible;
import fwcd.fructose.operations.Multipliable;
import fwcd.fructose.operations.Subtractable;

public interface AddSubMulDiv<V extends AddSubMulDiv<V>> extends
		Addable<V, V>,
		Subtractable<V, V>,
		Multipliable<V, V>,
		Divisible<V, V> {}
