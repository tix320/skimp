package com.github.tix320.skimp.test.generator;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.github.tix320.skimp.api.generator.IDGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Tigran Sargsyan on 23-Feb-19
 */
public class IDGeneratorTest {

	@Test
	public void simpleTest() {
		IDGenerator generator = new IDGenerator();

		assertEquals(Long.MIN_VALUE, generator.next());

		assertEquals(Long.MIN_VALUE + 1, generator.next());
		assertEquals(Long.MIN_VALUE + 2, generator.next());
	}

	@Test
	public void concurrentTest() {
		IDGenerator generator = new IDGenerator();

		int count = 500000;

		Set<Long> generated = new ConcurrentSkipListSet<>();

		Stream.generate(() -> null).limit(count).parallel().forEach(o -> generated.add(generator.next()));

		assertEquals(count, generated.size());
		Set<Long> expected = LongStream.range(Long.MIN_VALUE, Long.MIN_VALUE + count)
				.boxed()
				.collect(Collectors.toSet());
		assertEquals(expected, generated);
	}
}
