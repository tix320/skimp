package com.github.tix320.skimp.api.function;

public interface CheckedConsumer<T> {

	void accept(T t) throws Exception;
}
