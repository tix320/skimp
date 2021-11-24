package com.github.tix320.skimp.api.thread.tracer.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;

import com.github.tix320.skimp.api.thread.tracer.Tracer;

public final class DefaultTracer extends Tracer {

	private static final ThreadLocal<CallerInfo> CALLER_INFO_THREAD_LOCAL = new ThreadLocal<>();

	@Override
	public Runnable trackifyThreadTask(Runnable task) {
		Thread currentThread = Thread.currentThread();
		StackTraceElement[] stackTrace = concatCallerAndCurrent();

		CallerInfo callerInfo = new CallerInfo(currentThread, stackTrace);

		return () -> {
			CALLER_INFO_THREAD_LOCAL.set(callerInfo);
			try {
				task.run();
			}
			finally {
				CALLER_INFO_THREAD_LOCAL.remove();
			}
		};
	}

	@Override
	public <V> Callable<V> trackifyThreadTask(Callable<V> task) {
		Thread currentThread = Thread.currentThread();
		StackTraceElement[] stackTrace = concatCallerAndCurrent();

		CallerInfo callerInfo = new CallerInfo(currentThread, stackTrace);

		return () -> {
			CALLER_INFO_THREAD_LOCAL.set(callerInfo);
			try {
				return task.call();
			}
			finally {
				CALLER_INFO_THREAD_LOCAL.remove();
			}
		};
	}

	@Override
	public <T> Collection<? extends Callable<T>> trackifyThreadTasks(Collection<? extends Callable<T>> tasks) {
		Thread currentThread = Thread.currentThread();
		StackTraceElement[] stackTrace = concatCallerAndCurrent();

		CallerInfo callerInfo = new CallerInfo(currentThread, stackTrace);

		return tasks.stream().map(task -> (Callable<T>) () -> {
			CALLER_INFO_THREAD_LOCAL.set(callerInfo);
			try {
				return task.call();
			}
			finally {
				CALLER_INFO_THREAD_LOCAL.remove();
			}
		}).toList();
	}

	@Override
	public <T extends Throwable> void injectFullStacktrace(T throwable) {
		StackTraceElement[] stackTraceElements = concatCallerWith(throwable.getStackTrace());
		throwable.setStackTrace(stackTraceElements);
	}

	private StackTraceElement[] concatCallerAndCurrent() {
		CallerInfo callerInfo = CALLER_INFO_THREAD_LOCAL.get();

		Thread currentThread = Thread.currentThread();
		StackTraceElement[] currentThreadStacktrace = currentThread.getStackTrace();

		StackTraceElement[] combinedStacktrace;
		if (callerInfo == null) {
			currentThreadStacktrace = Arrays.copyOfRange(currentThreadStacktrace, 3, currentThreadStacktrace.length);
			combinedStacktrace = currentThreadStacktrace;
		}
		else {
			Thread callerThread = callerInfo.callerThread();
			StackTraceElement[] callerStackTrace = callerInfo.stackTrace();

			int currentStackTraceNewLength = currentThreadStacktrace.length - 3;
			StackTraceElement[] newStacktrace = new StackTraceElement[callerStackTrace.length + 1 + currentStackTraceNewLength];

			System.arraycopy(currentThreadStacktrace, 3, newStacktrace, 0, currentStackTraceNewLength);
			newStacktrace[currentStackTraceNewLength] = new StackTraceElement(
					"....Switching thread: %s".formatted(callerThread), "...%s".formatted(currentThread), "", -1);
			System.arraycopy(callerStackTrace, 0, newStacktrace, currentStackTraceNewLength + 1,
					callerStackTrace.length);

			combinedStacktrace = newStacktrace;
		}

		return combinedStacktrace;
	}

	private StackTraceElement[] concatCallerWith(StackTraceElement[] stackTrace) {
		CallerInfo callerInfo = CALLER_INFO_THREAD_LOCAL.get();

		StackTraceElement[] combinedStacktrace;
		if (callerInfo == null) {
			combinedStacktrace = stackTrace;
		}
		else {
			Thread callerThread = callerInfo.callerThread();
			Thread currentThread = Thread.currentThread();
			StackTraceElement[] callerStackTrace = callerInfo.stackTrace();

			StackTraceElement[] newStacktrace = new StackTraceElement[callerStackTrace.length + 1 + stackTrace.length];

			System.arraycopy(stackTrace, 0, newStacktrace, 0, stackTrace.length);
			newStacktrace[stackTrace.length] = new StackTraceElement(
					"....Switching thread: %s".formatted(callerThread), "...%s".formatted(currentThread), "", -1);
			System.arraycopy(callerStackTrace, 0, newStacktrace, stackTrace.length + 1, callerStackTrace.length);

			combinedStacktrace = newStacktrace;
		}

		return combinedStacktrace;
	}

	private static final record CallerInfo(Thread callerThread, StackTraceElement[] stackTrace) {}
}
