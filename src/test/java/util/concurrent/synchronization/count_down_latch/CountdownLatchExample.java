package util.concurrent.synchronization.count_down_latch;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

public class CountdownLatchExample {

	public class CountdownLatchCountExample {

		private int count;

		public CountdownLatchCountExample(int count) {
			this.count = count;
		}

		public boolean callTwiceInSameThread() {
			CountDownLatch countDownLatch = new CountDownLatch(count);
			Thread t = new Thread(() -> {
				countDownLatch.countDown();
				countDownLatch.countDown();
			});
			t.start();

			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return countDownLatch.getCount() == 0;
		}
	}

	@Test
	public void whenCountDownLatch_completed() {
		CountdownLatchCountExample ex = new CountdownLatchCountExample(2);
		boolean isCompleted = ex.callTwiceInSameThread();
		assertTrue(isCompleted);
	}


	public class CountdownLatchResetExample {
		private int count;
		private int threadCount;
		private final AtomicInteger updateCount;

		CountdownLatchResetExample(int count, int threadCount) {
			updateCount = new AtomicInteger(0);
			this.count = count;
			this.threadCount = threadCount;
		}

		public int countWaits() {
			CountDownLatch countDownLatch = new CountDownLatch(count);
			ExecutorService es = Executors.newFixedThreadPool(threadCount);
			for (int i = 0; i < threadCount; i++) {
				es.execute(() -> {
					long prevValue = countDownLatch.getCount();
					countDownLatch.countDown();
					if (countDownLatch.getCount() != prevValue) {
						updateCount.incrementAndGet();
					}
				});
			}

			es.shutdown();
			return updateCount.get();
		}
	}

	@Test
	public void whenCountDownLatch_noReset() {
		CountdownLatchResetExample ex = new CountdownLatchResetExample(7,20);
		int lineCount = ex.countWaits();
		assertTrue(lineCount <= 7);
	}
}
