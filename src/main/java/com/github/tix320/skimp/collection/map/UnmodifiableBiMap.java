package com.github.tix320.skimp.collection.map;

import com.github.tix320.skimp.collection.map.internal.AbstractBiMap;

/**
 * @author Tigran Sargsyan on 31-Mar-20.
 */
public final class UnmodifiableBiMap<F, S> extends AbstractBiMap<F, S> {

	private final BiMap<F, S> map;

	public UnmodifiableBiMap(BiMap<F, S> map) {
		this.map = map;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public S get(F key) {
		return map.get(key);
	}

	@Override
	public F getInverse(S key) {
		return map.getInverse(key);
	}

}
