package com.fwcd.fructose.math;

import java.util.function.DoubleUnaryOperator;

public class Approximator {
	private double inputGuess;
	private double searchedOutput;
	private DoubleUnaryOperator function;
	
	/**
	 * Constructs an Approximator that is capable of finding an input value
	 * that leads to a certain output value.
	 * 
	 * @param inputGues - An *input* value that leads to an output value near the searched value
	 * @param searchedOutput - The searched *output* value
	 * @param function - The function that takes an input value and converts it to an output value
	 */
	public Approximator(double inputGuess, double searchedOutput, DoubleUnaryOperator function) {
		this.inputGuess = inputGuess;
		this.searchedOutput = searchedOutput;
		this.function = function;
	}
	
	/**
	 * Finds the "correct" input value that leads to the
	 * specified and searched output value
	 * 
	 * @param certainty - The certainty specified in the amount of correct digits after the comma
	 * @return The correct input value up the given certainty
	 */
	public double approximateInput(int certainty) {
		double approxInput = inputGuess;
		double step = 1;
		
		while (Math.abs(step) > Math.pow(10, -(certainty + 1))) {
			double output = function.applyAsDouble(approxInput);
			
			if (output == searchedOutput) {
				return approxInput;
				
			} else if ((output < searchedOutput && step < 0) || (output > searchedOutput && step > 0)) {
				step /= 10; // Lower the step length
				step *= -1; // Invert the step direction
			}
			
			approxInput += step;
		}
		
		return approxInput;
	}
}