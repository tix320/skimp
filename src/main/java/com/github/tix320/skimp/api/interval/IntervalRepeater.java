package com.github.tix320.skimp.api.interval;


import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

import com.github.tix320.skimp.api.exception.ExceptionUtils;
import com.github.tix320.skimp.api.object.None;

public final class IntervalRepeater<T> {

	private final Supplier<T> action;

	private final Interval interval;

	private IntervalRepeater(Supplier<T> action, Interval interval) {
		this.action = action;
		this.interval = interval;
	}

	public T doNext() throws InterruptedException {
		Duration duration = interval.next();
		Thread.sleep(duration.toMillis());
		return action.get();
	}

	public Optional<T> doUntilSuccess(int maxCount) throws InterruptedException {
		int remaining = maxCount;
		while (remaining != 0) {
			try {
				return Optional.ofNullable(doNext());
			} catch (InterruptedException e) {
				throw e;
			} catch (Throwable e) {
				ExceptionUtils.applyToUncaughtExceptionHandler(e);
				remaining--;
			}
		}

		return Optional.empty();
	}

	public static <T> IntervalRepeater<T> of(Supplier<T> runnable, Interval interval) {
		return new IntervalRepeater<>(runnable, interval);
	}

	public static IntervalRepeater<None> of(Runnable runnable, Interval interval) {
		return of(() -> {
			runnable.run();
			return None.SELF;
		}, interval);
	}
}
