package zzk.study.java.core.util.concurrent.synchronization.semaphore;

import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SemaphoresManualTest {

    class LoginQueueUsingSemaphore {

        private final Semaphore semaphore;

        LoginQueueUsingSemaphore(int slotLimit) {
            semaphore = new Semaphore(slotLimit);
        }

        boolean tryLogin() {
            return semaphore.tryAcquire();
        }

        void logout() {
            semaphore.release();
        }

        int availableSlots() {
            return semaphore.availablePermits();
        }

    }


    // ========= login queue ======

    @Test
    public void givenLoginQueue_whenReachLimit_thenBlocked() throws InterruptedException {
        final int slots = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final LoginQueueUsingSemaphore loginQueue = new LoginQueueUsingSemaphore(slots);
        IntStream.range(0, slots)
          .forEach(user -> executorService.execute(loginQueue::tryLogin));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        assertEquals(0, loginQueue.availableSlots());
        assertFalse(loginQueue.tryLogin());
    }

    @Test
    public void givenLoginQueue_whenLogout_thenSlotsAvailable() throws InterruptedException {
        final int slots = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final LoginQueueUsingSemaphore loginQueue = new LoginQueueUsingSemaphore(slots);
        IntStream.range(0, slots)
          .forEach(user -> executorService.execute(loginQueue::tryLogin));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        assertEquals(0, loginQueue.availableSlots());
        loginQueue.logout();
        assertTrue(loginQueue.availableSlots() > 0);
        assertTrue(loginQueue.tryLogin());
    }

    // ========= delay queue =======

    class DelayQueueUsingTimedSemaphore {

        private final TimedSemaphore semaphore;

        DelayQueueUsingTimedSemaphore(long period, int slotLimit) {
            semaphore = new TimedSemaphore(period, TimeUnit.SECONDS, slotLimit);
        }

        boolean tryAdd() {
            return semaphore.tryAcquire();
        }

        int availableSlots() {
            return semaphore.getAvailablePermits();
        }

    }

    @Test
    public void givenDelayQueue_whenReachLimit_thenBlocked() throws InterruptedException {
        final int slots = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final DelayQueueUsingTimedSemaphore delayQueue = new DelayQueueUsingTimedSemaphore(1, slots);
        IntStream.range(0, slots)
          .forEach(user -> executorService.execute(delayQueue::tryAdd));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        assertEquals(0, delayQueue.availableSlots());
        assertFalse(delayQueue.tryAdd());
    }

    @Test
    public void givenDelayQueue_whenTimePass_thenSlotsAvailable() throws InterruptedException {
        final int slots = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final DelayQueueUsingTimedSemaphore delayQueue = new DelayQueueUsingTimedSemaphore(1, slots);
        IntStream.range(0, slots)
          .forEach(user -> executorService.execute(delayQueue::tryAdd));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        assertEquals(0, delayQueue.availableSlots());
        Thread.sleep(1000);
        assertTrue(delayQueue.availableSlots() > 0);
        assertTrue(delayQueue.tryAdd());
    }

    // ========== mutex ========
    class CounterUsingMutex {
        private final Semaphore mutex;
        private int count;

        CounterUsingMutex() {
            mutex = new Semaphore(1);
            count = 0;
        }

        void increase() throws InterruptedException {
            mutex.acquire();
            this.count = this.count + 1;
            Thread.sleep(1000);
            mutex.release();
        }

        int getCount() {
            return this.count;
        }

        boolean hasQueuedThreads() {
            return mutex.hasQueuedThreads();
        }
    }


    @Test
    public void whenMutexAndMultipleThreads_thenBlocked() throws InterruptedException {
        final int count = 5;
        final ExecutorService executorService = Executors.newFixedThreadPool(count);
        final CounterUsingMutex counter = new CounterUsingMutex();
        IntStream.range(0, count)
          .forEach(user -> executorService.execute(() -> {
              try {
                  counter.increase();
              } catch (final InterruptedException e) {
                  e.printStackTrace();
              }
          }));
        executorService.shutdown();

        assertTrue(counter.hasQueuedThreads());
    }

    @Test
    public void givenMutexAndMultipleThreads_ThenDelay_thenCorrectCount() throws InterruptedException {
        final int count = 5;
        final ExecutorService executorService = Executors.newFixedThreadPool(count);
        final CounterUsingMutex counter = new CounterUsingMutex();
        IntStream.range(0, count)
          .forEach(user -> executorService.execute(() -> {
              try {
                  counter.increase();
              } catch (final InterruptedException e) {
                  e.printStackTrace();
              }
          }));
        executorService.shutdown();
        assertTrue(counter.hasQueuedThreads());
        Thread.sleep(5000);
        assertFalse(counter.hasQueuedThreads());
        assertEquals(count, counter.getCount());
    }
}
