package com.github.tix320.skimp.thread;


import java.util.concurrent.locks.LockSupport;

import com.github.tix320.skimp.exception.ExceptionUtils;

/**
 * @author Tigran Sargsyan on 18-Apr-20.
 */
public final class LoopThread extends Thread {

	private final Runnable loopAction;

	public LoopThread(Runnable loopAction) {
		this.loopAction = loopAction;
	}

	@Override
	public void run() {
		Thread currentThread = Thread.currentThread();
		if (currentThread != this) {
			throw new IllegalStateException();
		}

		while (!currentThread.isInterrupted()) {
			try {
				loopAction.run();
			}
			catch (BreakLoopException e) {
				break;
			}
			catch (Throwable e) {
				ExceptionUtils.applyToUncaughtExceptionHandler(e);
			}
		}
	}

	public boolean isStarted() {
		return this.getState() != State.NEW;
	}

	public void unpark() {
		LockSupport.unpark(this);
	}

	public static final class BreakLoopException extends RuntimeException {

	}
}
