package zzk.study.java.core.util.concurrent.thread.solution;

import org.junit.Test;

import java.time.Instant;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

public class ThreadsStartAtSameTime {

    @Test
    public void usingCountDownLatch() throws InterruptedException {
        System.out.println("===============================================");
        System.out.println("        >>> Using CountDownLatch <<<<");
        System.out.println("===============================================");

        CountDownLatch latch = new CountDownLatch(1);

        WorkerWithCountDownLatch worker1 = new WorkerWithCountDownLatch("Worker with latch 1", latch);
        WorkerWithCountDownLatch worker2 = new WorkerWithCountDownLatch("Worker with latch 2", latch);

        worker1.start();
        worker2.start();

        Thread.sleep(10);//simulation of some actual work

        System.out.println("-----------------------------------------------");
        System.out.println(" Now release the latch:");
        System.out.println("-----------------------------------------------");
        latch.countDown();

        worker1.join();
        worker2.join();
    }

    @Test
    public void usingCyclicBarrier() throws BrokenBarrierException, InterruptedException {
        System.out.println("\n===============================================");
        System.out.println("        >>> Using CyclicBarrier <<<<");
        System.out.println("===============================================");

        CyclicBarrier barrier = new CyclicBarrier(3);

        WorkerWithCyclicBarrier worker1 = new WorkerWithCyclicBarrier("Worker with barrier 1", barrier);
        WorkerWithCyclicBarrier worker2 = new WorkerWithCyclicBarrier("Worker with barrier 2", barrier);

        worker1.start();
        worker2.start();

        Thread.sleep(10);//simulation of some actual work

        System.out.println("-----------------------------------------------");
        System.out.println(" Now open the barrier:");
        System.out.println("-----------------------------------------------");
        barrier.await();
    }

    @Test
    public void usingPhaser() throws InterruptedException {
        System.out.println("\n===============================================");
        System.out.println("        >>> Using Phaser <<<");
        System.out.println("===============================================");

        Phaser phaser = new Phaser();
        phaser.register();

        WorkerWithPhaser worker1 = new WorkerWithPhaser("Worker with phaser 1", phaser);
        WorkerWithPhaser worker2 = new WorkerWithPhaser("Worker with phaser 2", phaser);

        worker1.start();
        worker2.start();

        Thread.sleep(10);//simulation of some actual work

        System.out.println("-----------------------------------------------");
        System.out.println(" Now open the phaser barrier:");
        System.out.println("-----------------------------------------------");
        phaser.arriveAndAwaitAdvance();
    }

    static class WorkerWithCountDownLatch extends Thread {
        private CountDownLatch latch;

        public WorkerWithCountDownLatch(String name, CountDownLatch latch) {
            this.latch = latch;
            setName(name);
        }

        @Override public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the latch\n", getName());
                latch.await();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
                // do actual work here...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class WorkerWithCyclicBarrier extends Thread {
        private CyclicBarrier barrier;

        public WorkerWithCyclicBarrier(String name, CyclicBarrier barrier) {
            this.barrier = barrier;
            this.setName(name);
        }

        @Override public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the barrier\n", getName());
                barrier.await();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
                // do actual work here...
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    static class WorkerWithPhaser extends Thread {
        private Phaser phaser;

        public WorkerWithPhaser(String name, Phaser phaser) {
            this.phaser = phaser;
            phaser.register();
            setName(name);
        }

        @Override public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the phaser\n", getName());
                phaser.arriveAndAwaitAdvance();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
                // do actual work here...
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

}
