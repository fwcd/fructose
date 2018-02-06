package com.fwcd.fructose.quantum.core;

/**
 * Represents an operation on a {@link QubitSuperpos}.
 * 
 * @author Fredrik
 *
 */
@FunctionalInterface
public interface QubitOperation {
	QubitSuperpos apply(QubitSuperpos superpos);
}
