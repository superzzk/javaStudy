package zzk.study.java.core.util.concurrent.test;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * This is defined as a manual test because it tries to simulate the race conditions
 * in a concurrent program that is poorly designed and hence may fail nondeterministically.
 * This will help the CI jobs to ignore these tests and a developer to run them manually.
 *
 */
public class MyCounterSimpleManualTest {

	//单线程执行，肯定成功
	@Test
	public void testCounter() {
		MyCounter counter = new MyCounter();
		for (int i = 0; i < 500; i++)
			counter.increment();
		assertEquals(500, counter.getCount());
	}

	//并行执行，可能会出现错误
	//As we keep the number of threads low, like 10, we will notice that it passes almost all the time. Interestingly,
	// if we start increasing the number of threads, say to 100, we will see that the test starts to fail most of the time.
	@Test
	public void testCounterWithConcurrency() throws InterruptedException {
		int numberOfThreads = 100;
		ExecutorService service = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		MyCounter counter = new MyCounter();
		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				counter.increment();
				latch.countDown();
			});
		}
		latch.await();
		assertEquals(numberOfThreads, counter.getCount());
	}

	//必然错误
	@Test
	public void testSummationWithConcurrencyAndWait() throws InterruptedException {
		int numberOfThreads = 2;
		ExecutorService service = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		MyCounter counter = new MyCounter();
		for (int i = 0; i < numberOfThreads; i++) {
			service.submit(() -> {
				try {
					counter.incrementWithWait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();
			});
		}
		latch.await();
		assertEquals(numberOfThreads, counter.getCount());
	}

}
