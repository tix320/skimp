package com.github.tix320.skimp.interval;

/**
 * @author : Tigran Sargsyan
 * @since : 08.03.2021
 **/
public class ActionFailedException extends RuntimeException {

	public ActionFailedException(Throwable cause) {
		super("Interval action fails.", cause);
	}
}
