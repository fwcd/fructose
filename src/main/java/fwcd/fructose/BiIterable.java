package fwcd.fructose;

import java.util.function.BiConsumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface BiIterable<A, B> extends Iterable<Pair<A, B>> {
	@Override
	BiIterator<A, B> iterator();

	default Stream<Pair<A, B>> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	default void forEach(BiConsumer<? super A, ? super B> action) {
		BiIterator<A, B> it = iterator();
		while (it.hasNext()) {
			Pair<A, B> entry = it.next();
			action.accept(entry.getLeft(), entry.getRight());
		}
	}
}
