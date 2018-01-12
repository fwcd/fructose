package com.fredrikw.fructose;

@Deprecated
public class LogicUtils {
	public static boolean isBetween(double value, double boundA, double boundB) {
		return (value > boundA && value < boundB) || (value < boundA && value > boundB);
	}
}
