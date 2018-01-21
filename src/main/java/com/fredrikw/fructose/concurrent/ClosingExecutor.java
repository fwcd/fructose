package com.fredrikw.fructose.concurrent;

import java.io.Closeable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A convenient {@link ExecutorService} wrapper that
 * allows it to be placed inside a try-with-resources
 * statement and thus automatically shutdown.
 * 
 * @author Fredrik
 *
 */
public class ClosingExecutor implements ExecutorService, AutoCloseable, Closeable {
	private final ExecutorService delegate;
	
	public ClosingExecutor() {
		delegate = Executors.newCachedThreadPool();
	}
	
	public ClosingExecutor(int fixedThreadCount) {
		delegate = Executors.newFixedThreadPool(fixedThreadCount);
	}
	
	public ClosingExecutor(ExecutorService delegate) {
		this.delegate = delegate;
	}
	
	/**
	 * Invokes shutdown() on the delegate ExecutorService.
	 */
	@Override
	public void execute(Runnable command) {
		delegate.execute(command);
	}

	@Override
	public void close() {
		delegate.shutdown();
	}

	@Override
	public void shutdown() {
		delegate.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return delegate.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return delegate.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return delegate.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return delegate.awaitTermination(timeout, unit);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return delegate.submit(task);
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		return delegate.submit(task, result);
	}

	@Override
	public Future<?> submit(Runnable task) {
		return delegate.submit(task);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return delegate.invokeAll(tasks);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		return delegate.invokeAll(tasks, timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return delegate.invokeAny(tasks);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		return delegate.invokeAny(tasks, timeout, unit);
	}
}
