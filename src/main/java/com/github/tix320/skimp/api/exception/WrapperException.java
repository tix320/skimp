package com.github.tix320.skimp.api.exception;

public class WrapperException extends RuntimeException {

	private WrapperException(String message, Throwable cause) {
		super(message, cause);
	}

	public static RuntimeException wrap(Throwable throwable) {
		return new WrapperException("See the real cause", throwable);
	}

	public static void wrapAndThrow(Throwable throwable) {
		throw wrap(throwable);
	}
}
