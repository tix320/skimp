package com.github.tix320.skimp.api.thread.tracer.internal;

import java.util.Collection;
import java.util.concurrent.Callable;

import com.github.tix320.skimp.api.thread.tracer.Tracer;

public final class NoOpTracer extends Tracer {

	@Override
	public Runnable trackifyThreadTask(Runnable task) {
		return task;
	}

	@Override
	public <V> Callable<V> trackifyThreadTask(Callable<V> task) {
		return task;
	}

	@Override
	public <T> Collection<? extends Callable<T>> trackifyThreadTasks(Collection<? extends Callable<T>> tasks) {
		return tasks;
	}

	@Override
	public <T extends Throwable> void injectFullStacktrace(T throwable) {
	}
}
