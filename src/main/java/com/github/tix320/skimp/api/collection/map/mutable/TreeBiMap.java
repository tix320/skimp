package com.github.tix320.skimp.api.collection.map.mutable;

import java.util.Comparator;
import java.util.TreeMap;

import com.github.tix320.skimp.api.collection.map.mutable.internal.ComparatorBasedBiMap;

public final class TreeBiMap<F, S> extends ComparatorBasedBiMap<F, S> {

	public TreeBiMap(Comparator<? super F> firstComparator, Comparator<? super S> secondComparator) {
		super(new TreeMap<>(buildComparator(firstComparator, secondComparator)));
	}
}
