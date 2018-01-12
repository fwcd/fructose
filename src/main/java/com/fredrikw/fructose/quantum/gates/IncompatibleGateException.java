package com.fredrikw.fructose.quantum.gates;

import com.fredrikw.fructose.math.ComplexMatrix;
import com.fredrikw.fructose.math.ComplexVector;

public class IncompatibleGateException extends RuntimeException {
	private static final long serialVersionUID = -48723648763L;
	
	public IncompatibleGateException(String gateName, ComplexVector superpos, ComplexMatrix incompatibleMatrix) {
		super(
				"The quantum gate "
				+ gateName
				+ " requires a superposition with "
				+ Integer.toString(superpos.size())
				+ " states, not "
				+ Integer.toString(incompatibleMatrix.width())
				+ "!"
		);
	}
}
