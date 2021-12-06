package zzk.study.java.core.util.concurrent.lock.reentrant_lock;

import org.junit.Test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PerformanceTest {

    public enum LockType {JVM, JUC}

    public static LockType lockType;

    public static final long ITERATIONS = 5L * 1000L * 1000L;
    public static long counter = 0L;

    public static final Object jvmLock = new Object();
    public static final Lock jucLock = new ReentrantLock(false);

    /*
    * JUC的性能要好于JVM，同样执行一定次数的lock，JUC的时间约是1/3
    *       --------------  use JUC  ---------------
            0 thread, local counter = 583016
            1 thread, local counter = 678107
            2 thread, local counter = 614585
            3 thread, local counter = 588182
            4 thread, local counter = 611199
            5 thread, local counter = 602803
            6 thread, local counter = 671644
            7 thread, local counter = 650471
            duration:142874930
            --------------  use JVM  ---------------
            0 thread, local counter = 639945
            1 thread, local counter = 621455
            2 thread, local counter = 621249
            3 thread, local counter = 625472
            4 thread, local counter = 620442
            5 thread, local counter = 635195
            6 thread, local counter = 617803
            7 thread, local counter = 618446
            duration:413352506
    * */
    @Test
    public void test() throws Exception {
        long start;
        long duration;
        int numThreads = 8;
        System.out.println("--------------  use JUC  ---------------");
        lockType = LockType.JUC;

        start = System.nanoTime();
        runTest(numThreads, ITERATIONS);
        duration = System.nanoTime() - start;
        System.out.println("duration:" + duration);

        Thread.sleep(1000);
        counter = 0;
        System.out.println("--------------  use JVM  ---------------");
        lockType = LockType.JVM;

        start = System.nanoTime();
        runTest(numThreads, ITERATIONS);
        duration = System.nanoTime() - start;
        System.out.println("duration:" + duration);
    }

    private void runTest(final int numThreads, final long iteration)
            throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(numThreads);
        Thread[] threads = new Thread[numThreads];
        Task[] tasks = new Task[numThreads];
        for (int i = 0; i < numThreads; i++) {
            tasks[i] = new Task(iteration, barrier);
            threads[i] = new Thread(tasks[i]);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        int count = 0;
        for (int i = 0; i < threads.length; i++) {
            System.out.println(i + " thread, local counter = " + tasks[i].getLocalCounter());
            count += tasks[i].getLocalCounter();
        }
        System.out.println("count:"+count);
    }

    public static class Task implements Runnable {
        private final long iterationLimit;
        private final CyclicBarrier barrier;
        private long localCounter = 0L;

        public Task(long iterationLimit, CyclicBarrier barrier) {
            this.iterationLimit = iterationLimit;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                barrier.await();
            } catch (Exception e) {
                // don't care
            }

            switch (lockType) {
                case JVM:
                    jvmLockInc();
                    break;
                case JUC:
                    jucLockInc();
                    break;
            }
        }

        private void jvmLockInc() {
            while (true) {
                synchronized (jvmLock) {
                    if (counter >= iterationLimit) {
                        break;
                    }
                    ++counter;
                }
                localCounter++;
            }
        }

        private void jucLockInc() {
            while (true) {
                jucLock.lock();
                try {
                    if (counter >= iterationLimit) {
                        break;
                    }
                    ++counter;
                } finally {
                    jucLock.unlock();
                }
                localCounter++;
            }
        }

        public long getLocalCounter() {
            return localCounter;
        }
    }


}