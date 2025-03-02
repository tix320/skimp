package com.github.tix320.skimp.collection.map.internal;

import com.github.tix320.skimp.collection.map.BiMap;

public abstract class AbstractBiMap<F, S> implements BiMap<F, S> {

	@Override
	public final boolean contains(F key) {
		return get(key) != null;
	}

	@Override
	public final boolean containsInverse(S key) {
		return getInverse(key) != null;
	}

}
