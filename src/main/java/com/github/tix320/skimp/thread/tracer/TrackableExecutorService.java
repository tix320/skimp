package com.github.tix320.skimp.thread.tracer;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

import com.github.tix320.skimp.assertion.AssertionUtils;

public final class TrackableExecutorService implements ExecutorService {

	private final ExecutorService executorService;

	private TrackableExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public static ExecutorService wrap(ExecutorService executorService) {
		if (AssertionUtils.isEnabled()) {
			return new TrackableExecutorService(executorService);
		}
		else {
			return executorService;
		}
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return executorService.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return executorService.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return executorService.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return executorService.awaitTermination(timeout, unit);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return executorService.submit(Tracer.INSTANCE.trackifyThreadTask(task));
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		return executorService.submit(Tracer.INSTANCE.trackifyThreadTask(task), result);
	}

	@Override
	public Future<?> submit(Runnable task) {
		return executorService.submit(Tracer.INSTANCE.trackifyThreadTask(task));
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return executorService.invokeAll(Tracer.INSTANCE.trackifyThreadTasks(tasks));
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout,
										 TimeUnit unit) throws InterruptedException {
		return executorService.invokeAll(Tracer.INSTANCE.trackifyThreadTasks(tasks), timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return executorService.invokeAny(Tracer.INSTANCE.trackifyThreadTasks(tasks));
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout,
						   TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return executorService.invokeAny(Tracer.INSTANCE.trackifyThreadTasks(tasks), timeout, unit);
	}

	@Override
	public void execute(Runnable command) {
		executorService.execute(Tracer.INSTANCE.trackifyThreadTask(command));
	}
}
