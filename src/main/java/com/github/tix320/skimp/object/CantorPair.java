package com.github.tix320.skimp.object;

/**
 * @author Tigran Sargsyan on 28-Mar-20.
 */
public record CantorPair(long first, long second) {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CantorPair that = (CantorPair) o;
		return first == that.first && second == that.second;
	}

	@Override
	public int hashCode() {
		long sum = first + second;
		long cantorResult = ((sum * (sum + 1)) / 2) + second;
		return (int) cantorResult;
	}
}
