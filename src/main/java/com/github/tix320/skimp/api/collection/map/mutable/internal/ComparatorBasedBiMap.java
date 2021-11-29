package com.github.tix320.skimp.api.collection.map.mutable.internal;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.github.tix320.skimp.api.collection.map.BiMap;
import com.github.tix320.skimp.api.collection.map.internal.AbstractBiMap;
import com.github.tix320.skimp.api.collection.map.mutable.MutableSortedBiMap;

public abstract class ComparatorBasedBiMap<F, S> extends AbstractBiMap<F, S> implements MutableSortedBiMap<F, S> {

	private final SortedMap<EntryImpl<F, S>, EntryImpl<F, S>> backingMap;

	private final StraightView straightView;
	private final InverseView inverseView;

	protected ComparatorBasedBiMap(SortedMap<EntryImpl<F, S>, EntryImpl<F, S>> backingMap) {
		this.backingMap = backingMap;
		this.straightView = new StraightView();
		this.inverseView = new InverseView();
	}

	@Override
	public int size() {
		return backingMap.size();
	}

	@Override
	public final boolean isEmpty() {
		return backingMap.isEmpty();
	}

	@Override
	public final boolean contains(F key) {
		return straightView.containsKey(key);
	}

	@Override
	public final boolean inverseContains(S key) {
		return inverseView.containsKey(key);
	}

	@Override
	public final S get(F key) {
		return straightView.get(key);
	}

	@Override
	public final F inverseGet(S key) {
		return inverseView.get(key);
	}

	@Override
	public final Map<F, S> view() {
		return straightView;
	}

	@Override
	public final Map<S, F> inverseView() {
		return inverseView;
	}

	@Override
	public final Iterator<Entry<F, S>> iterator() {
		Iterator<Map.Entry<EntryImpl<F, S>, EntryImpl<F, S>>> iterator = backingMap.entrySet().iterator();

		return new Iterator<>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Entry<F, S> next() {
				return iterator.next().getKey();
			}
		};
	}

	@Override
	public final void put(F item1, S item2) {
		EntryImpl<F, S> entry = new EntryImpl<>(item1, item2);
		backingMap.put(entry, entry);
	}

	@Override
	public final S remove(F key) {
		EntryImpl<F, S> entry = new EntryImpl<>(key, null);
		EntryImpl<F, S> removedEntry = backingMap.remove(entry);
		return removedEntry == null ? null : removedEntry.second;
	}

	@Override
	public final F removeInverse(S key) {
		EntryImpl<F, S> entry = new EntryImpl<>(null, key);
		EntryImpl<F, S> removedEntry = backingMap.remove(entry);
		return removedEntry == null ? null : removedEntry.first;
	}

	@Override
	public final void clear() {
		backingMap.clear();
	}

	protected static <F, S> Comparator<EntryImpl<F, S>> buildComparator(Comparator<? super F> firstComparator,
																		Comparator<? super S> secondComparator) {
		return new EntryComparator<>(firstComparator, secondComparator);
	}

	protected record EntryImpl<F, S>(F first, S second) implements BiMap.Entry<F, S> {}

	private static final class EntryComparator<F, S> implements Comparator<EntryImpl<F, S>> {

		private final Comparator<? super F> firstComparator;
		private final Comparator<? super S> secondComparator;

		private EntryComparator(Comparator<? super F> firstComparator, Comparator<? super S> secondComparator) {
			this.firstComparator = firstComparator;
			this.secondComparator = secondComparator;
		}

		@Override
		public int compare(EntryImpl<F, S> entry1, EntryImpl<F, S> entry2) {
			if (entry1.first == null || entry2.first == null) {
				return secondComparator.compare(entry1.second, entry2.second);
			}
			else if (entry1.second == null || entry2.second == null) {
				return firstComparator.compare(entry1.first, entry2.first);
			}
			else {
				int compare = firstComparator.compare(entry1.first, entry2.first);
				if (compare == 0) {
					compare = secondComparator.compare(entry1.second, entry2.second);
				}

				return compare;
			}
		}
	}

	private abstract class AbstractView<K, V> implements Map<K, V> {

		@Override
		public final int size() {
			return backingMap.size();
		}

		@Override
		public final boolean isEmpty() {
			return backingMap.isEmpty();
		}

		@Override
		public final V put(K key, V value) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final V remove(Object key) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final void putAll(Map<? extends K, ? extends V> m) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final void clear() {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final boolean replace(K key, V oldValue, V newValue) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final V replace(K key, V value) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException("View collection");
		}

		@Override
		public final Set<K> keySet() {
			throw new UnsupportedOperationException("");
		}

		@Override
		public final Collection<V> values() {
			throw new UnsupportedOperationException("");
		}

		@Override
		public final Set<Entry<K, V>> entrySet() {
			throw new UnsupportedOperationException("");
		}

		@Override
		public final V putIfAbsent(K key, V value) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public final V getOrDefault(Object key, V defaultValue) {
			V value = get(key);
			return value == null ? defaultValue : value;
		}

	}

	private final class StraightView extends AbstractView<F, S> {

		@Override
		public boolean containsKey(Object key) {
			//noinspection unchecked
			return backingMap.containsKey(new EntryImpl<F, S>((F) key, null));
		}

		@Override
		public boolean containsValue(Object value) {
			//noinspection unchecked
			return backingMap.containsKey(new EntryImpl<F, S>(null, (S) value));
		}

		@Override
		public S get(Object key) {
			@SuppressWarnings("unchecked")
			EntryImpl<F, S> entry = backingMap.get(new EntryImpl<>((F) key, null));
			return entry == null ? null : entry.second;
		}

		@Override
		public void forEach(BiConsumer<? super F, ? super S> action) {
			for (BiMap.Entry<F, S> entry : ComparatorBasedBiMap.this) {
				action.accept(entry.first(), entry.second());
			}
		}
	}

	private final class InverseView extends AbstractView<S, F> {

		@Override
		public boolean containsKey(Object key) {
			//noinspection unchecked
			return backingMap.containsKey(new EntryImpl<F, S>(null, (S) key));
		}

		@Override
		public boolean containsValue(Object value) {
			//noinspection unchecked
			return backingMap.containsKey(new EntryImpl<F, S>((F) value, null));
		}

		@Override
		public F get(Object key) {
			@SuppressWarnings("unchecked")
			EntryImpl<F, S> entry = backingMap.get(new EntryImpl<F, S>(null, (S) key));
			return entry == null ? null : entry.first;
		}

		@Override
		public void forEach(BiConsumer<? super S, ? super F> action) {
			for (BiMap.Entry<F, S> entry : ComparatorBasedBiMap.this) {
				action.accept(entry.second(), entry.first());
			}
		}
	}
}
