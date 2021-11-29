package zzk.study.java.core.util.concurrent.executor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static junit.framework.TestCase.assertTrue;

public class WaitingForThreadsToFinishManualTest {

    private static final Logger LOG = LoggerFactory.getLogger(WaitingForThreadsToFinishManualTest.class);
    private final static ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    @Test
    public void givenMultipleThreads_whenUsingCountDownLatch_thenMainShoudWaitForAllToFinish() {

        ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);
        
        try {
            long startTime = System.currentTimeMillis();

            // create a CountDownLatch that waits for the 2 threads to finish
            CountDownLatch latch = new CountDownLatch(2);
            
            for (int i = 0; i < 2; i++) {
                WORKER_THREAD_POOL.submit(() -> {
                    try {
                        Thread.sleep(1000);
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                });
            }

            // wait for the latch to be decremented by the two threads
            latch.await();

            long processingTime = System.currentTimeMillis() - startTime;
            assertTrue(processingTime >= 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
    }

    @Test
    public void givenMultipleThreads_whenInvokeAll_thenMainThreadShouldWaitForAllToFinish() {

        ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = Arrays.asList(
            new DelayedCallable("fast thread", 100), 
            new DelayedCallable("slow thread", 3000));

        try {
            long startProcessingTime = System.currentTimeMillis();
            List<Future<String>> futures = WORKER_THREAD_POOL.invokeAll(callables);
            
            awaitTerminationAfterShutdown(WORKER_THREAD_POOL);

            try {
                WORKER_THREAD_POOL.submit((Callable<String>) () -> {
                    Thread.sleep(1000000);
                    return null;
                });
            } catch (RejectedExecutionException ex) {
                //
            }

            long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;
            assertTrue(totalProcessingTime >= 3000);

            String firstThreadResponse = futures.get(0)
                .get();
            assertTrue("First response should be from the fast thread", "fast thread".equals(firstThreadResponse));

            String secondThreadResponse = futures.get(1)
                .get();
            assertTrue("Last response should be from the slow thread", "slow thread".equals(secondThreadResponse));

        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }       
    }

    @Test
    public void givenMultipleThreads_whenUsingCompletionService_thenMainThreadShouldWaitForAllToFinish() {

        CompletionService<String> service = new ExecutorCompletionService<>(WORKER_THREAD_POOL);

        List<Callable<String>> callables = Arrays.asList(
            new DelayedCallable("fast thread", 100), 
            new DelayedCallable("slow thread", 3000));

        for (Callable<String> callable : callables) {
            service.submit(callable);
        }

        try {

            long startProcessingTime = System.currentTimeMillis();

            Future<String> future = service.take();
            String firstThreadResponse = future.get();
            long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

            assertTrue("First response should be from the fast thread", "fast thread".equals(firstThreadResponse));
            assertTrue(totalProcessingTime >= 100 && totalProcessingTime < 1000);
            LOG.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

            future = service.take();
            String secondThreadResponse = future.get();
            totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

            assertTrue("Last response should be from the slow thread", "slow thread".equals(secondThreadResponse));
            assertTrue(totalProcessingTime >= 3000 && totalProcessingTime < 4000);
            LOG.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
        }
    }

    public class DelayedCallable implements Callable<String> {

        private String name;
        private long period;
        private CountDownLatch latch;

        public DelayedCallable(String name, long period, CountDownLatch latch) {
            this(name, period);
            this.latch = latch;
        }

        public DelayedCallable(String name, long period) {
            this.name = name;
            this.period = period;
        }

        public String call() {

            try {
                Thread.sleep(period);

                if (latch != null) {
                    latch.countDown();
                }

            } catch (InterruptedException ex) {
                // handle exception
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }

            return name;
        }
    }
}
