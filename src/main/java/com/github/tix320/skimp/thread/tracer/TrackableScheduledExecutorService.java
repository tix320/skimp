package com.github.tix320.skimp.thread.tracer;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

import com.github.tix320.skimp.assertion.AssertionUtils;

public final class TrackableScheduledExecutorService implements ScheduledExecutorService {

	private final ScheduledExecutorService executorService;

	private TrackableScheduledExecutorService(ScheduledExecutorService executorService) {
		this.executorService = executorService;
	}

	public static ScheduledExecutorService wrap(ScheduledExecutorService executorService) {
		if (AssertionUtils.isEnabled()) {
			return new TrackableScheduledExecutorService(executorService);
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

	@Override
	public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		return executorService.schedule(Tracer.INSTANCE.trackifyThreadTask(command), delay, unit);
	}

	@Override
	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
		return executorService.schedule(Tracer.INSTANCE.trackifyThreadTask(callable), delay, unit);
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		return executorService.scheduleAtFixedRate(Tracer.INSTANCE.trackifyThreadTask(command), initialDelay, period,
				unit);
	}

	@Override
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		return executorService.scheduleWithFixedDelay(Tracer.INSTANCE.trackifyThreadTask(command), initialDelay, delay,
				unit);
	}
}
