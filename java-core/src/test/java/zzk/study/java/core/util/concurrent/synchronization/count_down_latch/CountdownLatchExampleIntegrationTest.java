package zzk.study.java.core.util.concurrent.synchronization.count_down_latch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CountdownLatchExampleIntegrationTest {
    @Test
    public void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion() throws InterruptedException {
        // Given
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream.generate(() -> new Thread(new Worker(outputScraper, countDownLatch)))
          .limit(5)
          .collect(toList());

        // When
        workers.forEach(Thread::start);
        countDownLatch.await(); // Block until workers finish
        outputScraper.add("Latch released");

        // Then
        assertThat(outputScraper).containsExactly("Counted down", "Counted down", "Counted down", "Counted down", "Counted down", "Latch released");
    }

    @Test
    public void whenFailingToParallelProcess_thenMainThreadShouldTimeout() throws InterruptedException {
        // Given
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream.generate(() -> new Thread(new BrokenWorker(outputScraper, countDownLatch)))
          .limit(5)
          .collect(toList());

        // When
        workers.forEach(Thread::start);
        final boolean result = countDownLatch.await(3L, TimeUnit.SECONDS);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    public void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime() throws InterruptedException {
        // Given
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch readyThreadCounter = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completedThreadCounter = new CountDownLatch(5);

        List<Thread> workers = Stream.generate(() -> new Thread(new WaitingWorker(outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter))).limit(5).collect(toList());

        // When
        workers.forEach(Thread::start);
        readyThreadCounter.await(); // Block until workers start
        outputScraper.add("Workers ready");
        callingThreadBlocker.countDown(); // Start workers
        completedThreadCounter.await(); // Block until workers finish
        outputScraper.add("Workers complete");

        // Then
        assertThat(outputScraper).containsExactly("Workers ready", "Counted down", "Counted down", "Counted down", "Counted down", "Counted down", "Workers complete");
    }

    public static class Worker implements Runnable {
        private final List<String> outputScraper;
        private final CountDownLatch countDownLatch;

        Worker(final List<String> outputScraper, final CountDownLatch countDownLatch) {
            this.outputScraper = outputScraper;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            // Do some work
            System.out.println("Doing some logic");
            outputScraper.add("Counted down");
            countDownLatch.countDown();
        }
    }

    public static class BrokenWorker implements Runnable {
        private final List<String> outputScraper;
        private final CountDownLatch countDownLatch;

        BrokenWorker(final List<String> outputScraper, final CountDownLatch countDownLatch) {
            this.outputScraper = outputScraper;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            if (true) {
                throw new RuntimeException("Oh dear");
            }
            countDownLatch.countDown();
            outputScraper.add("Counted down");
        }
    }

    public static class WaitingWorker implements Runnable {

        private final List<String> outputScraper;
        private final CountDownLatch readyThreadCounter;
        private final CountDownLatch callingThreadBlocker;
        private final CountDownLatch completedThreadCounter;

        WaitingWorker(final List<String> outputScraper, final CountDownLatch readyThreadCounter, final CountDownLatch callingThreadBlocker, CountDownLatch completedThreadCounter) {

            this.outputScraper = outputScraper;
            this.readyThreadCounter = readyThreadCounter;
            this.callingThreadBlocker = callingThreadBlocker;
            this.completedThreadCounter = completedThreadCounter;
        }

        @Override
        public void run() {
            // Mark this thread as read / started
            readyThreadCounter.countDown();
            try {
                callingThreadBlocker.await();
                outputScraper.add("Counted down");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                completedThreadCounter.countDown();
            }
        }
    }





}