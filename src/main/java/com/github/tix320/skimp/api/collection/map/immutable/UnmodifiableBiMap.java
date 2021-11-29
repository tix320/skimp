package com.github.tix320.skimp.api.collection.map.immutable;

import java.util.Iterator;
import java.util.Map;

import com.github.tix320.skimp.api.collection.UnmodifiableIterator;
import com.github.tix320.skimp.api.collection.map.BiMap;
import com.github.tix320.skimp.api.collection.map.internal.AbstractBiMap;

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
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean contains(F key) {
		return map.contains(key);
	}

	@Override
	public boolean inverseContains(S key) {
		return map.inverseContains(key);
	}

	@Override
	public S get(F key) {
		return map.get(key);
	}

	@Override
	public F inverseGet(S key) {
		return map.inverseGet(key);
	}

	@Override
	public Map<F, S> view() {
		return map.view();
	}

	@Override
	public Map<S, F> inverseView() {
		return map.inverseView();
	}

	@Override
	public Iterator<Entry<F, S>> iterator() {
		return new UnmodifiableIterator<>(map.iterator());
	}
}
