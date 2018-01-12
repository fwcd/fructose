package com.fredrikw.fructose.structs;

import com.fredrikw.fructose.BiIterable;
import com.fredrikw.fructose.Pair;

/**
 * A two-column list/table.
 * 
 * @author Fredrik
 *
 * @param <A> The first column item type
 * @param <B> The second column item type
 */
public interface BiList<A, B> extends BiIterable<A, B> {
	void add(A a, B b);
	
	void remove(int i);
	
	int size();
	
	Pair<A, B> get(int i);
}
