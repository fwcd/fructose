package fwcd.fructose.structs;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fwcd.fructose.StreamUtils;

public class IntList {
	private int[] data;
	private int size;
	
	public IntList() {
		this(10);
	}
	
	public IntList(int initialCapacity) {
		data = new int[initialCapacity];
		size = 0;
	}
	
	public IntList(int[] data) {
		data = Arrays.copyOf(data, data.length);
		size = data.length;
	}
	
	public IntList(IntStream stream) {
		data = stream.toArray();
		size = data.length;
	}
	
	public IntList(Iterable<Integer> iterable) {
		data = StreamUtils.stream(iterable)
			.mapToInt(Integer::valueOf)
			.toArray();
		size = data.length;
	}
	
	private void ensureCapacity() {
		if (size >= data.length - 1) {
			int[] newArr = Arrays.copyOf(data, size + 10);
			data = newArr;
		}
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void add(int v) {
		size++;
		ensureCapacity();
		data[size - 1] = v;
	}
	
	public void addAll(int... arr) {
		int offset = size;
		size += arr.length;
		ensureCapacity();
		System.arraycopy(arr, 0, data, offset, arr.length);
	}
	
	public void addAll(IntList list) {
		addAll(list.data);
	}
	
	public void removeLast() {
		size--;
	}
	
	public IntStream stream() {
		return Arrays.stream(data);
	}
	
	public List<Integer> boxed() {
		return stream().boxed().collect(Collectors.toList());
	}
	
	public int get(int i) {
		if (i < size) {
			return data[i];
		} else {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
	}
	
	public int[] toArray() {
		return Arrays.copyOf(data, size);
	}

	public long sum() {
		long result = 0;
		
		for (int i=0; i<size; i++) {
			result += data[i];
		}
		
		return result;
	}

	public int reduce(IntBinaryOperator associativeAccumulator) {
		int result = data[0];
		
		for (int i=1; i<size; i++) {
			result = associativeAccumulator.applyAsInt(result, data[i]);
		}
		
		return result;
	}
	
	public void forEach(IntConsumer consumer) {
		for (int i=0; i<size; i++) {
			consumer.accept(data[i]);
		}
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}

	@Override
	public boolean equals(Object obj) {
		return Arrays.equals(data, ((IntList) obj).data);
	}

	@Override
	public String toString() {
		return Arrays.toString(data);
	}
}
