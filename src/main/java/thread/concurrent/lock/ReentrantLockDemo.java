package thread.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock锁Demo
 * 它有一个与锁相关的获取计数器，如果拥有锁的某个线程再次得到锁，那么获取计数器就加1，然后锁需要被释放两次才能获得真正释放。
 *
 * 当并发量很小的时候，使用synchronized关键字的效率比Lock的效率高一点，而当并发量很高的时候，
 * Lock的性能就会比synchronized关键字高，具体的原因是因为synchronized关键字当竞争激烈的时候就会升级为重量级锁，
 * 而重量级锁的效率会变得非常的低下。
 */
public class ReentrantLockDemo {

    //库存
    class Depot {
        private int size;
        private Lock lock;

        public Depot() {
            this.size = 0;
            this.lock = new ReentrantLock();
        }

        //生产
        public void produce(int newSize) {
            lock.lock();
            try {
                size += newSize;
                System.out.println(Thread.currentThread().getName() + "---------" + size);
            } finally {
                lock.unlock();
            }
        }

        public void consume(int newSize) {
            lock.lock();
            try {
                size -= newSize;
                System.out.println(Thread.currentThread().getName() + "-----------" + size);
            } finally {
                lock.unlock();
            }
        }
    }

    //生产者
    class Producer {
        private Depot depot;

        public Producer(Depot depot) {
            this.depot = depot;
        }

        public void produce(final int newSize) {
            new Thread(() -> depot.produce(newSize)).start();
        }
    }

    //消费者
    class Customer {
        private Depot depot;

        public Customer(Depot depot) {
            this.depot = depot;
        }

        public void consume(final int newSize) {
            new Thread(() -> depot.consume(newSize)).start();
        }
    }

    public static void main(String[] args) {

        Depot depot = new ReentrantLockDemo().new Depot();
        Producer producer = new ReentrantLockDemo().new Producer(depot);
        Customer customer = new ReentrantLockDemo().new Customer(depot);

        producer.produce(60);
        producer.produce(120);
        customer.consume(90);
        customer.consume(150);
        producer.produce(110);
    }
}