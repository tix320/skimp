package com.github.tix320.skimp.collection.map;

import java.util.Iterator;
import java.util.Map;

public interface BiMap<F, S> extends Iterable<BiMap.Entry<F, S>> {

	int size();

	boolean isEmpty();

	boolean contains(F key);

	boolean inverseContains(S key);

	S get(F key);

	F inverseGet(S key);

	Map<F, S> view();

	Map<S, F> inverseView();

	Iterator<Entry<F, S>> iterator();

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
