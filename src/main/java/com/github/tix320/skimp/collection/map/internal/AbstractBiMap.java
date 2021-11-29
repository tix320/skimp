package com.github.tix320.skimp.collection.map.internal;

import java.util.Iterator;
import java.util.Objects;

import com.github.tix320.skimp.collection.map.BiMap;

public abstract class AbstractBiMap<F, S> implements BiMap<F, S> {

	@Override
	public final int hashCode() {
		int h = 0;
		for (Entry<F, S> entry : this) {
			h += entry.hashCode();
		}

		return h;
	}

	@Override
	public final boolean equals(Object o) {
		if (o == this) return true;

		if (!(o instanceof BiMap<?, ?>)) return false;

		@SuppressWarnings("unchecked")
		BiMap<F, S> m = (BiMap<F, S>) o;

		if (m.size() != size()) return false;

		try {
			for (Entry<F, S> e : this) {
				F f1 = e.first();
				S s1 = e.second();

				S s2 = m.get(f1);

				if (!Objects.equals(s1, s2)) {
					return false;
				}
			}
		}
		catch (ClassCastException unused) {
			return false;
		}

		return true;
	}

	public final String toString() {
		Iterator<Entry<F, S>> i = iterator();
		if (!i.hasNext()) return "{}";

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (; ; ) {
			Entry<F, S> e = i.next();
			F key = e.first();
			S value = e.second();
			sb.append(key == this ? "(this Map)" : key);
			sb.append('=');
			sb.append(value == this ? "(this Map)" : value);
			if (!i.hasNext()) return sb.append('}').toString();
			sb.append(',').append(' ');
		}
	}
}
