package zzk.study.java.core.util.concurrent.lock.reentrant_lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 当并发量很小的时候，使用synchronized关键字的效率比Lock的效率高一点，而当并发量很高的时候，
 * Lock的性能就会比synchronized关键字高，具体的原因是因为synchronized关键字当竞争激烈的时候就会升级为重量级锁，
 * 而重量级锁的效率会变得非常的低下。
 */
public class ReentrantLockDemo {

    @Test
    public void demo() {
        Depot depot = new Depot();
        Producer producer = new Producer(depot);
        Customer customer = new Customer(depot);

        producer.produce(60);
        producer.produce(120);
        customer.consume(90);
        customer.consume(150);
        producer.produce(110);
    }

    @Test
    public void isHeldByCurrentThread() {
        ReentrantLock lock = new ReentrantLock();

        try {
            Thread t = new Thread();
            System.out.println("     " + t.getThreadGroup());
            System.out.println("     " + t.isInterrupted());
            System.out.println("     " + t.getStackTrace());

            assert !lock.isHeldByCurrentThread();
            lock.lock();

            System.out.println(" after sleep(1500) Is held by Current Thread - " + lock.isHeldByCurrentThread());
        } finally {
            lock.unlock();
        }
    }


    //仓库
    static class Depot {
        private int size;
        private Lock lock;

        public Depot() {
            this.size = 0;
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
        }

        public void consume(int newSize) {
            lock.lock();
            try {
                size -= newSize;
                System.out.println(Thread.currentThread().getName() + "consume -----------" + size);
            } finally {
                lock.unlock();
            }
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