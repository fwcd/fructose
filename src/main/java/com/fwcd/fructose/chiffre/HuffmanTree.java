package com.fwcd.fructose.chiffre;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

import com.fwcd.fructose.structs.TreeNode;

/**
 * A binary huffman tree.
 *
 * @param <T> - The item data type
 */
public class HuffmanTree implements Comparable<HuffmanTree>, TreeNode, Serializable {
	private static final long serialVersionUID = -8986725416846768964L;
	private final boolean isLeaf;
	private HuffmanTree zero = null;
	private HuffmanTree one = null;
	private double probability;
	private char character;
	
	public HuffmanTree(char character, double probability) {
		this.probability = probability;
		this.character = character;
		isLeaf = true;
	}
	
	public HuffmanTree(HuffmanTree zero, HuffmanTree one) {
		this.zero = Objects.requireNonNull(zero);
		this.one = Objects.requireNonNull(one);
		probability = zero.probability + one.probability;
		isLeaf = false;
	}
	
	@Override
	public boolean isLeaf() {
		return isLeaf;
	}
	
	public boolean[] encode(char c) {
		if (isLeaf) {
			return c == character ? new boolean[0] : null;
		}
		
		if (zero != null) {
			boolean[] zeroPath = zero.encode(c);
			if (zeroPath != null) {
				return concat(new boolean[] {false}, zeroPath);
			}
		}
		if (one != null) {
			boolean[] onePath = one.encode(c);
			return onePath == null ? null : concat(new boolean[] {true}, onePath);
		}
		
		throw new IllegalStateException("Node can't have zero childs and be non-leaf.");
	}
	
	private boolean[] concat(boolean[] a, boolean[] b) {
		boolean[] res = new boolean[a.length + b.length];
		System.arraycopy(a, 0, res, 0, a.length);
		System.arraycopy(b, 0, res, a.length, b.length);
		return res;
	}
	
	public HuffmanTree getChild(boolean bit) {
		return bit ? one : zero;
	}

	public double getProbability() {
		return probability;
	}

	public char getCharacter() {
		return character;
	}
	
	@Override
	public int compareTo(HuffmanTree o) {
		return Double.compare(probability, o.probability);
	}
	
	public char decode(BooleanSupplier bitStream) {
		if (isLeaf) {
			return character;
		} else {
			return getChild(bitStream.getAsBoolean()).decode(bitStream);
		}
	}

	@Override
	public List<? extends TreeNode> getChildren() {
		return Arrays.asList(zero, one);
	}
}
