package com.github.tix320.skimp.thread.tracer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TracerExecutorServiceTest {

	private ExecutorService executorService;
	private ExecutorService tracerExecutorService;

	@BeforeEach
	public void setUp() {
		executorService = Executors.newCachedThreadPool(r -> {
			Thread thread = new Thread(r);
			thread.setName("test" + new Random().nextInt(30));
			thread.setDaemon(true);
			return thread;
		});
		tracerExecutorService = TracerExecutorService.wrap(executorService);
	}

	@Test
	public void shutdown() {
		assertFalse(executorService.isShutdown());
		assertFalse(tracerExecutorService.isShutdown());
		tracerExecutorService.shutdown();
		assertTrue(executorService.isShutdown());
		assertTrue(tracerExecutorService.isShutdown());
	}

	@Test
	public void shutdownNow() {
		assertFalse(executorService.isTerminated());
		assertFalse(tracerExecutorService.isTerminated());
		tracerExecutorService.shutdownNow();
		assertTrue(executorService.isTerminated());
		assertTrue(tracerExecutorService.isTerminated());
	}


	@Test
	public void submitRunnable() throws InterruptedException {
		List<Integer> items = new ArrayList<>();

		tracerExecutorService.submit(() -> {
			items.add(1);
			tracerExecutorService.submit(() -> {
				items.add(2);

				tracerExecutorService.submit(() -> {
					items.add(3);

					RuntimeException runtimeException;
					try {
						boo();
						throw new AssertionError();
					}
					catch (RuntimeException e) {
						runtimeException = e;
					}

					Tracer.INSTANCE.injectFullStacktrace(runtimeException);

					boolean containsSwitchFromMainToTestThread = Arrays.stream(runtimeException.getStackTrace())
							.anyMatch(stackTraceElement -> stackTraceElement.getClassName()
									.matches(
											".*Switching thread: Thread\\[main,5,main]") && stackTraceElement.getMethodName()
									.matches(".*Thread\\[test\\d{1,2},5,main]"));

					boolean containsSwitchFromTestToAnotherTestThread = Arrays.stream(runtimeException.getStackTrace())
							.anyMatch(stackTraceElement -> stackTraceElement.getClassName()
									.matches(
											".*Switching thread: Thread\\[test\\d{1,2},5,main]") && stackTraceElement.getMethodName()
									.matches(".*Thread\\[test\\d{1,2},5,main]"));

					boolean containsExceptionStacktrace = Arrays.stream(runtimeException.getStackTrace())
							.anyMatch(stackTraceElement -> stackTraceElement.getClassName()
									.matches(
											".*" + TracerExecutorServiceTest.class.getName()) && stackTraceElement.getMethodName()
									.equals("boo")) && Arrays.stream(runtimeException.getStackTrace())
							.anyMatch(stackTraceElement -> stackTraceElement.getClassName()
									.matches(
											".*" + TracerExecutorServiceTest.class.getName()) && stackTraceElement.getMethodName()
									.equals("doo"));

					if (containsSwitchFromMainToTestThread) {
						items.add(4);
					}

					if (containsSwitchFromTestToAnotherTestThread) {
						items.add(5);
					}

					if (containsExceptionStacktrace) {
						items.add(6);
					}

				});
			});
		});

		Thread.sleep(3000);

		assertEquals(List.of(1, 2, 3, 4, 5, 6), items);

	}

	private static void boo() {
		doo();
	}


	private static void doo() {
		throw new RuntimeException();
	}
}
