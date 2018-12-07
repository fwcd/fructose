package fwcd.fructose.function;

public interface ThrowingRunnable<E extends Throwable> {
	void run() throws E;
}
