package com.fredrikw.fructose.parsers;

public interface StringParser<T> {
	T parse(String raw);
}
