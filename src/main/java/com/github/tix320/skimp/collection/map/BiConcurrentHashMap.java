package com.github.tix320.skimp.collection.map;

import com.github.tix320.skimp.collection.map.internal.AbstractBiMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public final class BiConcurrentHashMap<F, S> extends AbstractBiMap<F, S> implements MutableBiMap<F, S> {

	private final Map<F, S> straight;
	private final Map<S, F> inverse;

	private final ReadLock readLock;
	private final WriteLock writeLock;

	public BiConcurrentHashMap() {
		this.straight = new ConcurrentHashMap<>();
		this.inverse = new ConcurrentHashMap<>();
		var lock = new ReentrantReadWriteLock();
		this.readLock = lock.readLock();
		this.writeLock = lock.writeLock();
	}

	@Override
	public void put(F key, S value) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(value);

		writeLock.lock();
		try {
			var previousValue = straight.put(key, value);
			if (previousValue != null && !previousValue.equals(value)) {
				inverse.remove(previousValue);
			}
			var previousKey = inverse.put(value, key);
			if (previousKey != null && !previousKey.equals(key)) {
				straight.remove(previousKey);
			}
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public S remove(F key) {
		Objects.requireNonNull(key);

		writeLock.lock();
		try {
			S value = straight.remove(key);
			if (value != null) {
				inverse.remove(value);
			}
			return value;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public F removeInverse(S key) {
		Objects.requireNonNull(key);

		writeLock.lock();
		try {
			F value = inverse.remove(key);
			if (value != null) {
				straight.remove(value);
			}
			return value;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public S get(F key) {
		readLock.lock();
		try {
			return straight.get(key);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public F getInverse(S key) {
		readLock.lock();
		try {
			return inverse.get(key);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public void clear() {
		writeLock.lock();
		try {
			straight.clear();
			inverse.clear();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public int size() {
		readLock.lock();
		try {
			return straight.size();
		} finally {
			readLock.unlock();
		}
	}

}
