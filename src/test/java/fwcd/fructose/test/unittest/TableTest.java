package fwcd.fructose.test.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fwcd.fructose.structs.MapTable;
import fwcd.fructose.structs.Table;

public class TableTest {
	@Test
	public void test() {
		Table<String, Integer, Double> t = new MapTable<>();
		t.put("test", 4, 9.5);
		t.put("demo", 56, 1.2);
		t.put("a", 87, 2.3);
		t.put("b", 91, 1.1);
		t.put("b", 12, 1.8);
		t.put("b", 12, 2.1);
		
		assertEquals(t.size(), 5);
		assertEquals(t.get("demo", 56), 1.2, 0.001D);
		assertEquals(t.get("b", 12), 2.1, 0.001D);
	}
}
