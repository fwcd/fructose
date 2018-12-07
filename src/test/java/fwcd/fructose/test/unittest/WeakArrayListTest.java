package fwcd.fructose.test.unittest;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;

import fwcd.fructose.structs.WeakArrayList;

import org.junit.Test;

public class WeakArrayListTest {
	private byte[] a = null;
	private byte[] b = null;
	private byte[] c = null;
	private byte[] d = null;
	private byte[] e = null;
	
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
		
		assertThat(list.size(), equalTo(3));
		assertThat(list, equalTo(weakListOf(a, b, c)));
		assertThat(list, not(equalTo(weakListOf(a, b))));
		assertThat(list, not(equalTo(weakListOf(a, b, c, c))));
		
		a = null;
		c = null;
		
		System.gc();
		assumeThat(list.size(), equalTo(1));
		
		assertThat(list.get(0), sameInstance(b));
		assertThat(list, equalTo(weakListOf(b)));
		assertThat(list, not(equalTo(weakListOf(a))));
		
		d = new byte[256];
		e = new byte[256];
		
		list.addAll(Arrays.asList(d, e));
		
		System.gc();
		assumeThat(list.size(), equalTo(3));
		
		b = null;
		d = null;
		
		System.gc();
		
		for (byte[] element : list) {
			assumeThat(element, sameInstance(e));
		}
		
		assumeThat(list.size(), equalTo(1));
	}
	
	@SafeVarargs
	private final <T> WeakArrayList<T> weakListOf(T... elements) {
		return new WeakArrayList<>(Arrays.asList(elements));
	}
}
