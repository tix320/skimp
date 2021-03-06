package com.github.tix320.skimp.api.thread;


import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Tigran Sargsyan on 18-Apr-20.
 */
public final class LoopThread {

	private final ThreadSubmitter threadSubmitter;

	private final LoopAction loopAction;

	private final AtomicReference<Thread> thread;

	public LoopThread(ThreadSubmitter threadSubmitter, LoopAction loopAction) {
		this.threadSubmitter = threadSubmitter;
		this.loopAction = loopAction;
		this.thread = new AtomicReference<>();
	}

	public void start() {
		threadSubmitter.submit(() -> {
			Thread thread = Thread.currentThread();
			this.thread.set(thread);
			while (!thread.isInterrupted()) {
				try {
					loopAction.run();
				}
				catch (BreakLoopException e) {
					break;
				}
				catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}

	public boolean isStarted() {
		return thread.get() != null;
	}

	public void stop() {
		Thread thread = this.thread.get();
		if (thread != null) {
			thread.interrupt();
		}
	}

	public void unpark() {
		LockSupport.unpark(thread.get());
	}

	public interface ThreadSubmitter {

		void submit(Runnable action);
	}

	public interface LoopAction {

		void run() throws BreakLoopException;
	}

	public static final class BreakLoopException extends Exception {

	}
}
