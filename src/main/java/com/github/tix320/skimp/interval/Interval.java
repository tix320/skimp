package com.github.tix320.skimp.interval;

import java.time.Duration;
import java.util.function.Function;

/**
 * @author : Tigran Sargsyan
 * @since : 14.02.2021
 **/
public class Interval {

	private final Duration initialDuration;

	private final Function<Duration, Duration> accumulator;

	private Duration currentDuration;

	public Interval(Duration initialDuration, Function<Duration, Duration> accumulator) {
		this.initialDuration = initialDuration;
		this.currentDuration = initialDuration;
		this.accumulator = accumulator;
	}

	public static Interval every(Duration duration) {
		return new Interval(Duration.ZERO, ignored -> duration);
	}

	public static Interval raising(Duration initial, Duration raise) {
		return new Interval(initial, previous -> previous.plus(raise));
	}

	public Duration next() {
		Duration duration = this.currentDuration;
		this.currentDuration = accumulator.apply(duration);
		return duration;
	}

	public Interval copyByInitialState() {
		return new Interval(initialDuration, accumulator);
	}
}
