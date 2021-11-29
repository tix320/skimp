package com.github.tix320.skimp.function;

public interface CheckedSupplier<T> {

	T get() throws Throwable;
}
