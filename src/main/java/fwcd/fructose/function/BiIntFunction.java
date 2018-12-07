package fwcd.fructose.function;

@FunctionalInterface
public interface BiIntFunction<T> {
	T apply(int a, int b);
}
