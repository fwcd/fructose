package com.fredrikw.fructose.exception;

public class SizeMismatchException extends RuntimeException {
	private static final long serialVersionUID = -2987364876345L;
	
	public SizeMismatchException(Object wrongSize, Object correctSize) {
		super(
				"The given size ("
				+ wrongSize.toString()
				+ ") does not match the correct size ("
				+ correctSize.toString()
				+ ")"
		);
	}
	
	public SizeMismatchException(String nameOfWrongSize, Object wrongSize, String nameOfCorrectSize, Object correctSize) {
		super(
				"The "
				+ nameOfWrongSize
				+ " ("
				+ wrongSize.toString()
				+ ") does not match the "
				+ nameOfCorrectSize
				+ " ("
				+ correctSize.toString()
				+ ")"
		);
	}
}
