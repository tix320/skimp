package com.github.tix320.skimp.function;

public interface CheckedFunction<T, R> {

	R apply(T t) throws Throwable;
}
