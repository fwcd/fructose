package com.fredrikw.fructose.quantum.core;

/**
 * Represents an operation on a {@link QubitSuperpos}.
 * 
 * @author Fredrik
 *
 */
public interface QubitOperation {
	public QubitSuperpos apply(QubitSuperpos superpos);
}
