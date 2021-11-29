package com.github.tix320.skimp.api.collection.map.mutable;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

import com.github.tix320.skimp.api.collection.map.mutable.internal.ComparatorBasedBiMap;

public final class ConcurrentSkipListBiMap<F, S> extends ComparatorBasedBiMap<F, S> {

	public ConcurrentSkipListBiMap(Comparator<? super F> firstComparator, Comparator<? super S> secondComparator) {
		super(new ConcurrentSkipListMap<>(buildComparator(firstComparator, secondComparator)));
	}
}
