package com.fwcd.fructose.ml.function;

import java.util.Map;

/**
 * A function that can be taught by showing it
 * examples of input-output-combinations. The exact
 * behavior is specified by the implementor.
 * 
 * @author Fredrik
 *
 * @param <I>
 * @param <O>
 */
public interface LearningFunction<I, O> {
	/**
	 * Calculates this function. There are no
	 * guarantees regarding purity, thread-safety or
	 * mutability.
	 * 
	 * @param input - The input
	 * @return The output
	 */
	O compute(I input);
	
	/**
	 * Teaches this function the given example
	 * input-output-mappings. The concrete behavior is
	 * up to the implementor, thus this interface
	 * does not guarantee any effect on the
	 * function's result.
	 * 
	 * @param examples - The example mappings
	 */
	void teach(Map<I, O> examples);
}
