package com.github.tix320.skimp.api.assertion;

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
