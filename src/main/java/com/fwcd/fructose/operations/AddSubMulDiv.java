package com.fwcd.fructose.operations;

import com.fwcd.fructose.operations.Addable;
import com.fwcd.fructose.operations.Divisible;
import com.fwcd.fructose.operations.Multipliable;
import com.fwcd.fructose.operations.Subtractable;

public interface AddSubMulDiv<V extends AddSubMulDiv<V>> extends
		Addable<V, V>,
		Subtractable<V, V>,
		Multipliable<V, V>,
		Divisible<V, V> {}
