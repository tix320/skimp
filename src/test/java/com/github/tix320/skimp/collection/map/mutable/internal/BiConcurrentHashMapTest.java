package com.github.tix320.skimp.collection.map.mutable.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.tix320.skimp.collection.map.BiConcurrentHashMap;
import org.junit.jupiter.api.Test;

public class BiConcurrentHashMapTest {

	@Test
	public void size() {
		var map = new BiConcurrentHashMap<Integer, Integer>();
		assertEquals(0, map.size());

		map.put(3, 4);
		map.put(4, 5);

		assertEquals(2, map.size());

		map.remove(4);

		assertEquals(1, map.size());
	}

	@Test
	public void contains() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);

		assertTrue(map.contains(4));
		assertFalse(map.contains(3));
		assertFalse(map.contains(5));
	}

	@Test
	public void containsInverse() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);

		assertTrue(map.containsInverse(5));
		assertFalse(map.containsInverse(6));
		assertFalse(map.containsInverse(4));
	}

	@Test
	public void get() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);

		assertEquals(5, map.get(4));
		assertNull(map.get(3));
		assertNull(map.get(5));
	}

	@Test
	public void getInverse() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);

		assertEquals(4, map.getInverse(5));
		assertNull(map.getInverse(6));
		assertNull(map.getInverse(4));
	}

	@Test
	public void put() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);

		assertEquals(5, map.get(4));
		assertNull(map.get(5));
	}

	@Test
	public void remove() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);

		assertTrue(map.contains(4));

		var removed = map.remove(4);

		assertFalse(map.contains(4));
		assertEquals(5, removed);
	}

	@Test
	public void removeInverse() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);

		assertTrue(map.contains(4));

		var removed = map.removeInverse(5);

		assertFalse(map.contains(4));
		assertEquals(4, removed);
	}

	@Test
	public void clear() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);
		map.put(7, 8);
		map.put(1, 2);

		assertTrue(map.contains(4));
		assertTrue(map.contains(7));
		assertTrue(map.containsInverse(2));

		map.clear();

		assertFalse(map.contains(4));
		assertFalse(map.contains(7));
		assertFalse(map.containsInverse(2));
	}

	@Test
	public void duplicateExactKey() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);
		map.put(4, 5);

		assertEquals(1, map.size());
		assertEquals(5, map.get(4));
		assertEquals(4, map.getInverse(5));
	}

	@Test
	public void duplicatePartialKey() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(4, 5);
		map.put(4, 7);

		assertEquals(1, map.size());
		assertEquals(7, map.get(4));
		assertEquals(4, map.getInverse(7));
		assertNull(map.getInverse(5));
	}

	@Test
	public void duplicatePartialInverseKey() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(6, 5);
		map.put(8, 5);

		assertEquals(1, map.size());
		assertEquals(5, map.get(8));
		assertEquals(8, map.getInverse(5));
		assertNull(map.get(6));
	}

	@Test
	public void messyOperations() {
		var map = new BiConcurrentHashMap<Integer, Integer>();

		map.put(1, 10);
		map.put(2, 20);
		map.put(3, 30);

		assertEquals(20, map.get(2));
		assertEquals(2, map.getInverse(20));

		map.put(2, 25);
		assertNull(map.getInverse(20));
		assertEquals(25, map.get(2));

		map.remove(3);
		assertNull(map.get(3));
		assertNull(map.getInverse(30));

		map.put(3, 35); // Add back
		assertEquals(35, map.get(3));

		map.removeInverse(25);
		assertNull(map.get(2));
		assertNull(map.getInverse(25));

		map.put(4, 40);
		map.put(5, 50);

		assertEquals(40, map.get(4));
		assertEquals(50, map.get(5));

		map.put(4, 400);
		assertEquals(400, map.get(4));
		assertNull(map.getInverse(40));

		map.removeInverse(50);
		assertNull(map.get(5));

		assertEquals(400, map.get(4));
	}

}
