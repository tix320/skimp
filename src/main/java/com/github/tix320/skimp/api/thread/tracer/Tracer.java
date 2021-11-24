package com.github.tix320.skimp.api.thread.tracer;

import java.util.Collection;
import java.util.concurrent.Callable;

import com.github.tix320.skimp.api.assertion.AssertionUtils;
import com.github.tix320.skimp.api.thread.tracer.internal.DefaultTracer;
import com.github.tix320.skimp.api.thread.tracer.internal.NoOpTracer;

public sealed abstract class Tracer permits DefaultTracer, NoOpTracer {

	public static final Tracer INSTANCE;

	static {
		INSTANCE = AssertionUtils.isEnabled() ? new DefaultTracer() : new NoOpTracer();
	}

	public abstract Runnable trackifyThreadTask(Runnable task);

	public abstract <V> Callable<V> trackifyThreadTask(Callable<V> task);

	public abstract <T> Collection<? extends Callable<T>> trackifyThreadTasks(Collection<? extends Callable<T>> tasks);

	public abstract <T extends Throwable> void injectFullStacktrace(T throwable);
}
