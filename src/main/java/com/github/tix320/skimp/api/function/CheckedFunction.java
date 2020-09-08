package com.github.tix320.skimp.api.function;

public interface CheckedFunction<T, R> {

	R apply(T t) throws Exception;
}
