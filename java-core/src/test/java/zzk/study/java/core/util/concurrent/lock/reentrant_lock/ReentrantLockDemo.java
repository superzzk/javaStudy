package zzk.study.java.core.util.concurrent.lock.reentrant_lock;

import org.junit.Assert;
import org.junit.Test;

import java.time.chrono.ThaiBuddhistEra;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * 当并发量很小的时候，使用synchronized关键字的效率比Lock的效率高一点，而当并发量很高的时候，
 * Lock的性能就会比synchronized关键字高，具体的原因是因为synchronized关键字当竞争激烈的时候就会升级为重量级锁，
 * 而重量级锁的效率会变得非常的低下。
 */
public class ReentrantLockDemo {

    @Test
    public void demo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        Depot depot = new Depot(latch);

        Producer producer = new Producer(depot);
        Customer customer = new Customer(depot);

        producer.produce(60);
        producer.produce(120);
        customer.consume(90);
        customer.consume(150);
        producer.produce(110);

        latch.await();
    }

    @Test
    public void isHeldByCurrentThread() {
        ReentrantLock lock = new ReentrantLock();

        try {
            Assert.assertFalse(lock.isHeldByCurrentThread());
            lock.lock();
            Assert.assertTrue(lock.isHeldByCurrentThread());
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void givenReentrantLock_whenLockAndUnlock_thenCheckHoldCountAndIsLocked() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        try {
            reentrantLock.lock();
            assertEquals(1, reentrantLock.getHoldCount());
            assertTrue(reentrantLock.isLocked());
        } finally {
            reentrantLock.unlock();
            assertEquals(0, reentrantLock.getHoldCount());
            assertFalse(reentrantLock.isLocked());
        }
    }

    @Test
    public void givenReentrantLock_whenLockMultipleTimes_thenUnlockMultipleTimesToRelease() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        try {
            reentrantLock.lock();
            reentrantLock.lock();
            assertEquals(2, reentrantLock.getHoldCount());
            assertEquals(true, reentrantLock.isLocked());
        } finally {
            reentrantLock.unlock();
            assertEquals(1, reentrantLock.getHoldCount());
            assertEquals(true, reentrantLock.isLocked());

            reentrantLock.unlock();
            assertEquals(0, reentrantLock.getHoldCount());
            assertEquals(false, reentrantLock.isLocked());
        }
    }

    // ? 如何测试公平锁和非公平锁，以下两个用例测不出来
    @Test
    public void non_fair_lock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(false);

        Thread[] threadArray = new Thread[30];
        for (int i=0; i<30; i++) {
            threadArray[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"启动");
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() +"获得了锁");
                }finally {
                    lock.unlock();
                }
            });
        }
        lock.lock();
        for (int i=0; i<30; i++) {
            threadArray[i].start();
        }
        lock.unlock();
        Thread.sleep(200);
    }

    @Test
    public void fair_lock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);

        Thread[] threadArray = new Thread[30];
        for (int i=0; i<30; i++) {
            threadArray[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"启动");
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() +"获得了锁");
                }finally {
                    lock.unlock();
                }
            });
        }
        lock.lock();
        for (int i=0; i<30; i++) {
            threadArray[i].start();
        }
        lock.unlock();
        Thread.sleep(200);
    }


    //仓库
    static class Depot {
        private int size;
        private Lock lock;
        private CountDownLatch latch;

        public Depot(CountDownLatch latch) {
            this.size = 0;
            this.latch = latch;
            this.lock = new ReentrantLock();
        }

        public void produce(int newSize) {
            lock.lock();
            try {
                size += newSize;
                System.out.println(Thread.currentThread().getName() + "produce ---------" + size);
            } finally {
                lock.unlock();
            }
            latch.countDown();
        }

        public void consume(int newSize) {
            lock.lock();
            try {
                size -= newSize;
                System.out.println(Thread.currentThread().getName() + "consume -----------" + size);
            } finally {
                lock.unlock();
            }
            latch.countDown();
        }
    }

    //生产者
    static class Producer {
        private Depot depot;


        public Producer(Depot depot) {
            this.depot = depot;
        }

        public void produce(final int newSize) {
            new Thread(() -> depot.produce(newSize)).start();
        }
    }

    //消费者
    static class Customer {
        private Depot depot;

        public Customer(Depot depot) {
            this.depot = depot;
        }

        public void consume(final int newSize) {
            new Thread(() -> depot.consume(newSize)).start();
        }
    }


}