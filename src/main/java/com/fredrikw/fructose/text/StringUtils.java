package com.fredrikw.fructose.text;

public class StringUtils {
	public static String capitalize(String text) {
		return Character.toUpperCase(text.toCharArray()[0]) + text.substring(1).toLowerCase();
	}
}
