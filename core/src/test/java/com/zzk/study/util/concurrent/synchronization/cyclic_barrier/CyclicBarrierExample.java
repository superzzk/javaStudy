package com.zzk.study.util.concurrent.synchronization.cyclic_barrier;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CyclicBarrierExample {
	public class CyclicBarrierCountExample {

		private int count;

		public CyclicBarrierCountExample(int count) {
			this.count = count;
		}

		public boolean callTwiceInSameThread() {
			CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
			Thread t = new Thread(() -> {
				try {
					cyclicBarrier.await();
					cyclicBarrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
			t.start();
			return cyclicBarrier.isBroken();
		}
	}

	@Test
	public void whenCyclicBarrier_notCompleted() {
		CyclicBarrierCountExample ex = new CyclicBarrierCountExample(2);
		boolean isCompleted = ex.callTwiceInSameThread();
		assertFalse(isCompleted);
	}

	public class CyclicBarrierResetExample {
		private int count;
		private int threadCount;
		private final AtomicInteger updateCount;

		CyclicBarrierResetExample(int count, int threadCount) {
			updateCount = new AtomicInteger(0);
			this.count = count;
			this.threadCount = threadCount;
		}

		public int countWaits() {
			CyclicBarrier cyclicBarrier = new CyclicBarrier(count);

			ExecutorService es = Executors.newFixedThreadPool(threadCount);
			for (int i = 0; i < threadCount; i++) {
				es.execute(() -> {
					try {
						if (cyclicBarrier.getNumberWaiting() > 0) {
							updateCount.incrementAndGet();
						}
						cyclicBarrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				});
			}
			es.shutdown();
			try {
				es.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return updateCount.get();
		}
	}

	@Test
	public void whenCyclicBarrier_reset() {
		CyclicBarrierResetExample ex = new CyclicBarrierResetExample(7,20);
		int lineCount = ex.countWaits();
		assertTrue(lineCount > 7);
	}
}
