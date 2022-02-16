package com.github.tix320.skimp.collection.map.mutable.internal;

import java.util.stream.Stream;

import com.github.tix320.skimp.collection.map.mutable.ConcurrentSkipListBiMap;
import com.github.tix320.skimp.collection.map.mutable.MutableBiMap;
import com.github.tix320.skimp.collection.map.mutable.TreeBiMap;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.*;

public class ComparatorBasedBiMapTest {

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void size(MutableBiMap<Integer, Integer> map) {
		System.out.println(map);
		assertEquals(0, map.size());

		map.put(3, 4);
		map.put(4, 5);

		assertEquals(2, map.size());

		map.remove(4);

		assertEquals(1, map.size());
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void isEmpty(MutableBiMap<Integer, Integer> map) {
		assertTrue(map.isEmpty());

		map.put(1, 2);

		assertFalse(map.isEmpty());
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void contains(MutableBiMap<Integer, Integer> map) {
		map.put(4, 5);

		assertTrue(map.contains(4));
		assertFalse(map.contains(3));
		assertFalse(map.contains(5));
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void containsInverse(MutableBiMap<Integer, Integer> map) {
		map.put(4, 5);

		assertTrue(map.inverseContains(5));
		assertFalse(map.inverseContains(6));
		assertFalse(map.inverseContains(4));
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void get(MutableBiMap<Integer, Integer> map) {
		map.put(4, 5);

		assertEquals(5, map.get(4));
		assertNull(map.get(3));
		assertNull(map.get(5));
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void getInverse(MutableBiMap<Integer, Integer> map) {
		map.put(4, 5);

		assertEquals(4, map.inverseGet(5));
		assertNull(map.inverseGet(6));
		assertNull(map.inverseGet(4));
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void put(MutableBiMap<Integer, Integer> map) {
		map.put(4, 5);

		assertEquals(5, map.get(4));
		assertNull(map.get(5));
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void remove(MutableBiMap<Integer, Integer> map) {
		map.put(4, 5);

		assertTrue(map.contains(4));

		map.remove(4);

		assertFalse(map.contains(4));
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void removeInverse(MutableBiMap<Integer, Integer> map) {
		map.put(4, 5);

		assertTrue(map.contains(4));

		map.removeInverse(5);

		assertFalse(map.contains(4));
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void clear(MutableBiMap<Integer, Integer> map) {
		map.put(4, 5);
		map.put(7, 8);
		map.put(1, 2);

		assertTrue(map.contains(4));
		assertTrue(map.contains(7));
		assertTrue(map.inverseContains(2));

		map.clear();

		assertFalse(map.contains(4));
		assertFalse(map.contains(7));
		assertFalse(map.inverseContains(2));
	}

	@ParameterizedTest
	@ArgumentsSource(DoubleMapArgumentsProvider.class)
	public void equals(MutableBiMap<Integer, Integer> map1, MutableBiMap<Integer, Integer> map2) {
		map1.put(4, 5);
		map1.put(1, 2);

		assertNotEquals(map1, map2);

		map2.put(4, 5);
		map2.put(1, 2);

		assertEquals(map1, map2);
	}

	@ParameterizedTest
	@ArgumentsSource(DoubleMapArgumentsProvider.class)
	public void inverseNotEquals(MutableBiMap<Integer, Integer> map1, MutableBiMap<Integer, Integer> map2) {
		map1.put(4, 5);
		map1.put(1, 2);

		map2.put(5, 4);
		map2.put(2, 1);

		assertNotEquals(map1, map2);
	}

	@ParameterizedTest
	@ArgumentsSource(MapArgumentsProvider.class)
	public void bigTest(MutableBiMap<Integer, Integer> map) {
		map.put(1, 2);
		map.put(3, 4);
		map.put(5, 6);
		map.put(7, 8);
		map.put(-2, -1);
		map.put(-5, -7);
		map.put(-12, -6);

		assertEquals(2, map.get(1));
		assertEquals(8, map.get(7));
		assertEquals(-7, map.get(-5));
		assertEquals(1, map.inverseGet(2));
		assertEquals(-2, map.inverseGet(-1));
		assertEquals(-12, map.inverseGet(-6));
	}

	private static class MapArgumentsProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			return Stream.of(Arguments.of(new TreeBiMap<>(Integer::compare, Integer::compare)),
					Arguments.of(new ConcurrentSkipListBiMap<>(Integer::compare, Integer::compare)));
		}
	}

	private static class DoubleMapArgumentsProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			return Stream.of(Arguments.of(new TreeBiMap<>(Integer::compare, Integer::compare),
							new TreeBiMap<>(Integer::compare, Integer::compare)),
					Arguments.of(new ConcurrentSkipListBiMap<>(Integer::compare, Integer::compare),
							new ConcurrentSkipListBiMap<>(Integer::compare, Integer::compare)));
		}
	}
}
