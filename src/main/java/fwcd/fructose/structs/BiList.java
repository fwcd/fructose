package fwcd.fructose.structs;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fwcd.fructose.BiIterable;
import fwcd.fructose.Pair;

/**
 * A two-column list/table.
 * 
 * @author Fredrik
 *
 * @param <L> The first column item type
 * @param <R> The second column item type
 */
public interface BiList<L, R> extends BiIterable<L, R> {
	void add(L a, R b);
	
	void add(int i, L a, R b);
	
	void remove(L a);
	
	void remove(L a, R b);
	
	void remove(int i);
	
	boolean containsLeft(L a);
	
	boolean containsRight(R b);
	
	boolean contains(L a, R b);
	
	int size();
	
	int indexOf(L a);
	
	int indexOf(L a, R b);
	
	void remap(L a, R b);
	
	L getLeft(int i);
	
	R getRight(int i);
	
	void setLeft(int i, L value);
	
	void setRight(int i, R value);
	
	default Pair<L, R> get(int i) {
		return new Pair<L, R>(getLeft(i), getRight(i));
	}
	
	default List<L> getLeftList() {
		return IntStream.range(0, size())
			.mapToObj(this::getLeft)
			.collect(Collectors.toList());
	}
	
	default List<R> getRightList() {
		return IntStream.range(0, size())
			.mapToObj(this::getRight)
			.collect(Collectors.toList());
	}
	
	default void addAll(BiList<? extends L, ? extends R> list) {
		list.forEach(this::add);
	}

	@Override
	default void forEach(BiConsumer<? super L, ? super R> action) {
		for (int i=0; i<size(); i++) {
			action.accept(getLeft(i), getRight(i));
		}
	}
	
	void clear();
	
	@Deprecated
	default L getA(int i) { return getLeft(i); }
	
	@Deprecated
	default R getB(int i) { return getRight(i); }
	
	@Deprecated
	default void setA(int i, L value) { setLeft(i, value); }
	
	@Deprecated
	default void setB(int i, R value) { setRight(i, value); }
	
	@Deprecated
	default boolean containsA(L a) { return containsLeft(a); }
	
	@Deprecated
	default boolean containsB(R b) { return containsRight(b); }
}
