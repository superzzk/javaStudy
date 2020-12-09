package zzk.study.java.core.util.concurrent.synchronization.cyclic_barrier;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class CyclicBarrierCompletionMethodExampleUnitTest {
    
    @Test
    public void whenCyclicBarrier_countTrips() {
        CyclicBarrierCompletionMethodExample ex = new CyclicBarrierCompletionMethodExample(7,20);
        int lineCount = ex.countTrips();
        assertEquals(2, lineCount);
    }

    public static class CyclicBarrierCompletionMethodExample {

        private int count;
        private int threadCount;
        private final AtomicInteger updateCount;

        CyclicBarrierCompletionMethodExample(int count, int threadCount) {
            updateCount = new AtomicInteger(0);
            this.count = count;
            this.threadCount = threadCount;
        }

        public int countTrips() {

            CyclicBarrier cyclicBarrier = new CyclicBarrier(count, () -> {
                updateCount.incrementAndGet();
            });

            ExecutorService es = Executors.newFixedThreadPool(threadCount);
            for (int i = 0; i < threadCount; i++) {
                es.execute(() -> {
                    try {
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
}
