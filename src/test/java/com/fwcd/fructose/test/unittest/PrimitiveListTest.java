package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fwcd.fructose.structs.DoubleList;

public class PrimitiveListTest {
	@Test
	public void test() {
		DoubleList list = new DoubleList();
		list.add(4);
		list.add(5);
		list.addAll(new DoubleList(new double[] {9, 1}));
		list.addAll(8, 4, 6);
		
		DoubleList comparision = new DoubleList(new double[] {4, 5, 9, 1, 8, 4, 6});
		assertEquals(list, comparision);
		assertEquals(list.size(), 7);
		assertEquals(list.filter(v -> v % 2 == 0), new DoubleList(new double[] {4, 8, 4, 6}));
		
		list.remove(5, 2);
		assertArrayEquals(list.toArray(), new double[] {4, 5, 1, 8, 6}, 0.01D);
	}
}
