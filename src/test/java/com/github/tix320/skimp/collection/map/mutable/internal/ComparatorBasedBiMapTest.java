package com.github.tix320.skimp.collection.map.mutable.internal;

import com.github.tix320.skimp.collection.map.mutable.TreeBiMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComparatorBasedBiMapTest {

	@Test
	public void size() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);
		assertEquals(0, map.size());

		map.put(3, 4);
		map.put(4, 5);

		assertEquals(2, map.size());

		map.remove(4);

		assertEquals(1, map.size());
	}

	@Test
	public void isEmpty() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);
		assertTrue(map.isEmpty());

		map.put(1, 2);

		assertFalse(map.isEmpty());
	}

	@Test
	public void contains() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

		map.put(4, 5);

		assertTrue(map.contains(4));
		assertFalse(map.contains(3));
		assertFalse(map.contains(5));
	}

	@Test
	public void containsInverse() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

		map.put(4, 5);

		assertTrue(map.inverseContains(5));
		assertFalse(map.inverseContains(6));
		assertFalse(map.inverseContains(4));
	}

	@Test
	public void get() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

		map.put(4, 5);

		assertEquals(5, map.get(4));
		assertNull(map.get(3));
		assertNull(map.get(5));
	}

	@Test
	public void getInverse() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

		map.put(4, 5);

		assertEquals(4, map.inverseGet(5));
		assertNull(map.inverseGet(6));
		assertNull(map.inverseGet(4));
	}

	@Test
	public void put() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

		map.put(4, 5);

		assertEquals(5, map.get(4));
		assertNull(map.get(5));
	}

	@Test
	public void remove() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

		map.put(4, 5);

		assertTrue(map.contains(4));

		map.remove(4);

		assertFalse(map.contains(4));
	}

	@Test
	public void removeInverse() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

		map.put(4, 5);

		assertTrue(map.contains(4));

		map.removeInverse(5);

		assertFalse(map.contains(4));
	}

	@Test
	public void clear() {
		TreeBiMap<Integer, Integer> map = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

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

	@Test
	public void equals() {
		TreeBiMap<Integer, Integer> map1 = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);
		TreeBiMap<Integer, Integer> map2 = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

		map1.put(4, 5);
		map1.put(1, 2);

		assertNotEquals(map1, map2);

		map2.put(4, 5);
		map2.put(1, 2);

		assertEquals(map1, map2);
	}

	@Test
	public void inverseNotEquals() {
		TreeBiMap<Integer, Integer> map1 = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);
		TreeBiMap<Integer, Integer> map2 = new TreeBiMap<>(Integer::compareTo, Integer::compareTo);

		map1.put(4, 5);
		map1.put(1, 2);

		map2.put(5, 4);
		map2.put(2, 1);

		assertNotEquals(map1, map2);
	}
}
