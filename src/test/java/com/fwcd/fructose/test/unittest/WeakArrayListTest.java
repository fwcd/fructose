package com.fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fwcd.fructose.structs.WeakArrayList;

import org.junit.Test;

public class WeakArrayListTest {
	private byte[] a = null;
	private byte[] b = null;
	private byte[] c = null;
	
	@Test
	public void testWeakArrayList() {
		a = new byte[256];
		b = new byte[256];
		c = new byte[256];
		
		List<byte[]> list = new WeakArrayList<>();
		assertTrue(list.isEmpty());
		
		list.add(a);
		list.add(b);
		list.add(c);
		
		assertEquals(3, list.size());
		
		a = null;
		c = null;
		
		System.gc();
		assumeTrue(list.size() == 1);
		
		assertSame(b, list.get(0));
		assertEquals(new ArrayList<>(Collections.singleton(b)), list);
	}
}
