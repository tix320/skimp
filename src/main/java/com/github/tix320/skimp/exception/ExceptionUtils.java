package com.github.tix320.skimp.exception;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author Tigran Sargsyan on 23-Jun-20.
 */
public class ExceptionUtils {

	public static void applyToUncaughtExceptionHandler(Throwable t) {
		Thread thread = Thread.currentThread();
		UncaughtExceptionHandler uncaughtExceptionHandler = thread.getUncaughtExceptionHandler();
		try {
			uncaughtExceptionHandler.uncaughtException(thread, t);
		}
		catch (Throwable e) {
			RuntimeException runtimeException = new RuntimeException("Exception in uncaughtExceptionHandler", e);
			runtimeException.printStackTrace();
		}
	}

	public static void appendStacktraceToThrowable(Throwable throwable, StackTraceElement[] asyncRunnerStackTrace) {
		StackTraceElement[] realStacktrace = throwable.getStackTrace();

		StackTraceElement[] newStacktrace = new StackTraceElement[asyncRunnerStackTrace.length + realStacktrace.length];

		System.arraycopy(realStacktrace, 0, newStacktrace, 0, realStacktrace.length);
		System.arraycopy(asyncRunnerStackTrace, 0, newStacktrace, realStacktrace.length, asyncRunnerStackTrace.length);

		throwable.setStackTrace(newStacktrace);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Throwable> void sneakyThrow(Throwable e) throws T {
		throw (T) e;
	}
}
