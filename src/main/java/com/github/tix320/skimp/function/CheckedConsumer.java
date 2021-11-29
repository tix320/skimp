package com.github.tix320.skimp.function;

public interface CheckedConsumer<T> {

	void accept(T t) throws Throwable;
}
