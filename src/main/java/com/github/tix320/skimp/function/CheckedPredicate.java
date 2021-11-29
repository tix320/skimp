package com.github.tix320.skimp.function;

public interface CheckedPredicate<T> {

	boolean test(T t) throws Throwable;
}
