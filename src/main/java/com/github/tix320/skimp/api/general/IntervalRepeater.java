package com.github.tix320.skimp.api.general;


import java.time.Duration;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.github.tix320.skimp.api.exception.ExceptionUtils;
import com.github.tix320.skimp.api.object.None;

public final class IntervalRepeater<T> {

	private final Supplier<T> action;

	private final Function<Duration, Duration> accumulator;

	private final Duration initialDuration;

	private Duration currentDuration;

	public IntervalRepeater(Supplier<T> action, Duration initialDuration, Function<Duration, Duration> accumulator) {
		this.action = action;
		this.accumulator = accumulator;
		this.initialDuration = initialDuration;
		this.currentDuration = initialDuration;
	}

	public T doNext() throws InterruptedException {
		Duration duration = this.currentDuration;
		this.currentDuration = accumulator.apply(duration);
		Thread.sleep(duration.toMillis());
		return action.get();
	}

	public Optional<T> doUntilSuccess(int maxCount) throws InterruptedException {
		while (maxCount != 0) {
			try {
				return Optional.ofNullable(doNext());
			} catch (InterruptedException e) {
				throw e;
			} catch (Throwable e) {
				ExceptionUtils.applyToUncaughtExceptionHandler(e);
				maxCount--;
			}
		}

		return Optional.empty();
	}

	public IntervalRepeater<T> copyByInitialState() {
		return new IntervalRepeater<T>(action, initialDuration, accumulator);
	}

	public static <T> IntervalRepeater<T> every(Supplier<T> runnable, Duration duration) {
		return new IntervalRepeater<>(runnable, Duration.ZERO, ignored -> duration);
	}

	public static IntervalRepeater<None> every(Runnable runnable, Duration duration) {
		return every(() -> {
			runnable.run();
			return None.SELF;
		}, duration);
	}

	public static <T> IntervalRepeater<T> raising(Supplier<T> runnable, Duration initial, Duration raise) {
		return new IntervalRepeater<>(runnable, initial, previous -> previous.plus(raise));
	}

	public static IntervalRepeater<None> raising(Runnable runnable, Duration initial, Duration raise) {
		return raising(() -> {
			runnable.run();
			return None.SELF;
		}, initial, raise);
	}
}
