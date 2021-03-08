package com.github.tix320.skimp.api.interval;


import java.time.Duration;
import java.util.Optional;

import com.github.tix320.skimp.api.exception.ExceptionUtils;
import com.github.tix320.skimp.api.exception.ThreadInterruptedException;
import com.github.tix320.skimp.api.function.CheckedRunnable;
import com.github.tix320.skimp.api.function.CheckedSupplier;
import com.github.tix320.skimp.api.object.None;

public final class IntervalRepeater<T> {

	private final Interval interval;

	private final CheckedSupplier<T> action;

	private IntervalRepeater(Interval interval, CheckedSupplier<T> action) {
		this.interval = interval;
		this.action = action;
	}

	public T doNext() throws ThreadInterruptedException, ActionFailedException {
		Duration duration = interval.next();
		try {
			Thread.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			throw new ThreadInterruptedException();
		}
		try {
			return action.get();
		} catch (Exception e) {
			throw new ActionFailedException(e);
		}
	}

	public Optional<T> doUntilSuccess(int maxCount) throws ThreadInterruptedException {
		int remaining = maxCount;
		while (remaining != 0) {
			try {
				return Optional.ofNullable(doNext());
			} catch (ThreadInterruptedException e) {
				throw new ThreadInterruptedException();
			} catch (ActionFailedException e) {
				remaining--;
				ExceptionUtils.applyToUncaughtExceptionHandler(e);
			}
		}

		return Optional.empty();
	}

	public static <T> IntervalRepeater<T> of(Interval interval, CheckedSupplier<T> runnable) {
		return new IntervalRepeater<>(interval, runnable);
	}

	public static IntervalRepeater<None> of(Interval interval, CheckedRunnable runnable) {
		return of(interval, () -> {
			runnable.run();
			return None.SELF;
		});
	}
}
