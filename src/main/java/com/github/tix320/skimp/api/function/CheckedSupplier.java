package com.github.tix320.skimp.api.function;

public interface CheckedSupplier<T> {

	T get() throws Throwable;
}
