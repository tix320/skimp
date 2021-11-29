package com.github.tix320.skimp.thread;


/**
 * @author Tigran Sargsyan on 27-Mar-20.
 */
public final class Threads {

	public static Thread simple(Runnable runnable) {
		return new Thread(runnable);
	}

	public static Thread daemon(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		return thread;
	}

	public static LoopThread loop(Runnable loopAction) {
		return new LoopThread(loopAction);
	}

	public static LoopThread daemonLoop(Runnable loopAction) {
		LoopThread loopThread = new LoopThread(loopAction);
		loopThread.setDaemon(true);
		return loopThread;
	}
}
