package com.fredrikw.fructose.math.expressions;

public interface Equation {
	Term[] getEqualTerms();
	
	Term solveFor(Variable variable, Term... others);
}
