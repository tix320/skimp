package com.github.tix320.skimp.api.collection.map.mutable;

import com.github.tix320.skimp.api.collection.map.BiMap;

public interface MutableBiMap<F, S> extends BiMap<F, S> {

	void put(F item1, S item2);

	S remove(F key);

	F removeInverse(S key);

	void clear();
}
