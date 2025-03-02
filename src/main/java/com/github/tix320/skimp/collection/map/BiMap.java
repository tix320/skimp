package com.github.tix320.skimp.collection.map;

public interface BiMap<F, S> {

	int size();

	boolean contains(F key);

	boolean containsInverse(S key);

	S get(F key);

	F getInverse(S key);

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	interface Entry<F, S> {

		F first();

		S second();

		@Override
		boolean equals(Object o);

		@Override
		int hashCode();

	}

}
