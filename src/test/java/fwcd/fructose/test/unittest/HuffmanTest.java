package fwcd.fructose.test.unittest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fwcd.fructose.chiffre.huffman.Huffman;
import fwcd.fructose.chiffre.huffman.HuffmanTree;

public class HuffmanTest {
	@Test
	public void test() {
		HuffmanTree tree = new HuffmanTree(
				new HuffmanTree('a', 0.5D),
				new HuffmanTree(
						new HuffmanTree('b', 0.5D),
						new HuffmanTree('c', 0.5D)
				)
		);
		assertArrayEquals(new boolean[] {false}, tree.encode('a'));
		assertArrayEquals(new boolean[] {true, false}, tree.encode('b'));
		assertArrayEquals(new boolean[] {true, true}, tree.encode('c'));
		
		Huffman huffman = new Huffman();
		assertEquals("test123", huffman.decode(huffman.encode("test123")));
	}
}
