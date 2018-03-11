package com.fwcd.fructose.chiffre;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.fwcd.fructose.Distribution;
import com.fwcd.fructose.Distribution.Normalizer;
import com.fwcd.fructose.exception.Rethrow;

/**
 * An implementation of the
 * lossless Huffman compression algorithm.
 */
public class Huffman implements StringEncoding {
	@Override
	public byte[] encode(String data) {
		Queue<HuffmanTree> queue = toPriorityQueue(distributionOf(data));
		
		while (queue.size() > 1) {
			// Pick the two items with the lowest probability (thus highest priority)
			HuffmanTree a = queue.poll();
			HuffmanTree b = queue.poll();
			
			// Probabilities of the child trees are added together
			queue.offer(new HuffmanTree(a, b));
		}
		
		HuffmanTree tree = queue.poll();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(tree);
			
			for (char c : data.toCharArray()) {
				for (boolean bit : tree.encode(c)) {
					oos.writeBoolean(bit);
				}
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		return baos.toByteArray();
	}

	@Override
	public String decode(byte[] data) {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		StringBuilder s = new StringBuilder();
		
		try (ObjectInputStream ois = new ObjectInputStream(bais)) {
			HuffmanTree tree = (HuffmanTree) ois.readObject();
			
			while (ois.available() > 0) {
				s.append(tree.decode(() -> {
					try {
						return ois.readBoolean();
					} catch (IOException e) { throw new UncheckedIOException(e); }
				}));
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (ClassNotFoundException e) {
			throw new Rethrow(e);
		}
		
		return s.toString();
	}
	
	private Distribution<Character> distributionOf(String input) {
		Map<Character, Double> dist = new HashMap<>();
		
		for (char c : input.toCharArray()) {
			if (dist.containsKey(c)) {
				dist.put(c, dist.get(c) + 1D);
			} else {
				dist.put(c, 0D);
			}
		}
		
		return new Distribution<>(dist, Normalizer.NORMALIZE);
	}
	
	private Queue<HuffmanTree> toPriorityQueue(Distribution<Character> dist) {
		Queue<HuffmanTree> queue = new PriorityQueue<>();
		dist.forEach((c, probability) -> queue.add(new HuffmanTree(c, probability)));
		return queue;
	}
}
