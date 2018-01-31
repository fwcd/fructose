package com.fwcd.fructose.quantum.gates;

import com.fwcd.fructose.math.ComplexMatrix;
import com.fwcd.fructose.math.ComplexVector;

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
