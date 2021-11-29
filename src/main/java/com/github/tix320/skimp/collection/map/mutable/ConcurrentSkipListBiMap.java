package com.github.tix320.skimp.collection.map.mutable;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

import com.github.tix320.skimp.collection.map.mutable.internal.ComparatorBasedBiMap;
import com.github.tix320.skimp.thread.ThreadSafe;

public final class ConcurrentSkipListBiMap<F, S> extends ComparatorBasedBiMap<F, S> implements ThreadSafe {

	public ConcurrentSkipListBiMap(Comparator<? super F> firstComparator, Comparator<? super S> secondComparator) {
		super(new ConcurrentSkipListMap<>(buildComparator(firstComparator, secondComparator)));
	}
}
