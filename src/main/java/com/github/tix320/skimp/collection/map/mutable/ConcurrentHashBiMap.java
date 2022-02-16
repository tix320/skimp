package com.github.tix320.skimp.collection.map.mutable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentHashMap;

import com.github.tix320.skimp.collection.map.internal.AbstractBiMap;
import com.github.tix320.skimp.collection.map.mutable.internal.ComparatorBasedBiMap;

public final class ConcurrentHashBiMap<F, S> extends AbstractBiMap<F, S> implements MutableBiMap<F,S> {

	private final ConcurrentHashMap<F,S> backingMap;
	private final ConcurrentHashMap<F,S> inverseBackingMap;

	private final ConcurrentHashMap<F, S> straightView;
	private final ConcurrentHashMap<S, F> inverseView;

	public ConcurrentHashBiMap() {
		straight = new ConcurrentHashMap<>();
		straightView = Collections.unmodifiableMap(straight);
		inverse = new ConcurrentHashMap<>();
		inverseView = Collections.unmodifiableMap(inverse);
	}

	@Override
	public synchronized void put(F key, S value) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(value);
		straight.put(key, value);
		inverse.put(value, key);
	}

	@Override
	public synchronized S straightRemove(F key) {
		Objects.requireNonNull(key);
		S value = straight.remove(key);
		if (value == null) {
			return null;
		}
		inverse.remove(value);
		return value;
	}

	@Override
	public synchronized F inverseRemove(S key) {
		Objects.requireNonNull(key);
		F value = inverse.remove(key);
		if (value == null) {
			return null;
		}
		straight.remove(value);
		return value;
	}

	@Override
	public Map<F, S> straightView() {
		return straightView;
	}

	@Override
	public Map<S, F> inverseView() {
		return inverseView;
	}

	@Override
	public int hashCode() {
		return straight.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof BiMap)) {
			return false;
		}

		BiMap<?, ?> that = (BiMap<?, ?>) obj;

		return straight.equals(that.straightView());
	}

	@Override
	public String toString() {
		return straight.toString();
	}
}
