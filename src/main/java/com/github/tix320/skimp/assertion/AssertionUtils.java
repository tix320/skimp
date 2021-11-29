package com.github.tix320.skimp.assertion;

public final class AssertionUtils {

	public static boolean isEnabled() {
		try {
			assert false;
			return false;
		}
		catch (AssertionError e) {
			return true;
		}
	}
}
