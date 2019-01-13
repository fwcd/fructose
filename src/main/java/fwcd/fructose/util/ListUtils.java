package fwcd.fructose.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import fwcd.fructose.function.BiIntFunction;

/**
 * Array and List utilities.
 */
public final class ListUtils {
	private ListUtils() {}
	
	public static <T> List<T> makeList(int length, IntFunction<T> generator) {
		List<T> result = new ArrayList<>();
		for (int i=0; i<length; i++) {
			result.add(generator.apply(i));
		}
		return result;
	}
	
	public static <T> List<List<T>> make2DList(int yLength, int xLength, BiIntFunction<T> generator) {
		List<List<T>> result = new ArrayList<>();
		for (int y=0; y<yLength; y++) {
			List<T> row = new ArrayList<>();
			for (int x=0; x<xLength; x++) {
				row.add(generator.apply(x, y));
			}
			result.add(row);
		}
		return result;
	}
	
	public static int toInt(int[] digits) {
		int num = 0;
		int f = (int) Math.pow(10, digits.length - 1);
		
		for (int digit : digits) {
			num += (digit * f);
			f /= 10;
		}
		
		return num;
	}
	
	public static int toInt(Collection<Integer> digits) {
		int num = 0;
		int f = (int) Math.pow(10, digits.size() - 1);
		
		for (int digit : digits) {
			num += (digit * f);
			f /= 10;
		}
		
		return num;
	}
	
	public static <T> List<T> join(T[][] arrays) {
		List<T> merged = new ArrayList<>();
		
		for (T[] array : arrays) {
			merged.addAll(Arrays.asList(array));
		}
		
		return merged;
	}
	
	public static List<?> join(List<?> list1, List<?> list2) {
		return join(new List<?>[] {list1, list2});
	}
	
	@SuppressWarnings("unchecked")
	public static List<?> join(List<?>[] lists) {
		List<Object> newList = new ArrayList<>(lists[0]);
		
		int i = 0;
		for (List<Object> list : (List<Object>[]) lists) {
			if (i != 0) {
				newList.addAll(list);
			}
			
			i++;
		}
		
		return newList;
	}
	
	public static Integer[] box(int[] primitiveInts) {
		return IntStream.of(primitiveInts).boxed().toArray(Integer[]::new);
	}
	
	public static void swap(int[] array, int value1, int value2) {
		int val1Pos = Integer.MIN_VALUE;
		int val2Pos = Integer.MIN_VALUE;
		
		for (int i=0; i<array.length; i++) {
			if (array[i] == value1) {
				val1Pos = i;
			} else if (array[i] == value2) {
				val2Pos = i;
			}
		}
		
		if (val1Pos == Integer.MIN_VALUE || val2Pos == Integer.MIN_VALUE) {
			throw new RuntimeException("Given values could not be found in the array.");
		}
		
		array[val2Pos] = value1;
		array[val1Pos] = value2;
	}
	
	public static void cycle(int[] intArray) {
		int[] prev = intArray.clone();
		
		for (int i=0; i<intArray.length; i++) {
			if (i == 0) {
				intArray[i] = prev[intArray.length - 1];
			} else {
				intArray[i] = prev[i - 1];
			}
		}
	}
	
	/**
	 * Concatenates the list items to a string.
	 * 
	 * @param collection
	 * @return
	 */
	public static String concat(Iterable<? extends Object> collection) {
		String s = "";
		
		for (Object item : collection) {
			s += item.toString();
		}

		return s;
	}
	
	// ==== Methods for dynamic (lambda-filtered) list modification ====
	
	/**
	 * Removes items from the collection that apply to the condition.
	 * 
	 * @param collection - The collection
	 * @param condition - The condition/filter (takes the index as an argument)
	 */
	public static void filteredRemove(Collection<? extends Object> collection, IntFunction<Boolean> condition) {
		int i = 0;
		for (Object item : collection) {
			if (condition.apply(i)) {
				collection.remove(item);
			}
			
			i++;
		}
	}
	
	/**
	 * Clears and dynamically fills a list using a given supplier function.
	 * 
	 * @param list - The list
	 * @param generator - The supplier function (takes the index as an argument)
	 * @param length - The desired length of the collection
	 */
	public static <T> void dynamicFill(List<T> list, IntFunction<? extends T> generator, int length) {
		list.clear();
		
		for (int i=0; i<length; i++) {
			list.add(generator.apply(i));
		}
	}
}
