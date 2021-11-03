package com.github.tix320.skimp.api.function;

public interface CheckedPredicate<T> {

	boolean test(T t) throws Throwable;
}
