package zzk.study.java.core.util.concurrent.lock.reentrant_lock;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWithCondition {

    private static Logger logger = LoggerFactory.getLogger(ReentrantLockWithCondition.class);

    @Test
    public void demo() throws InterruptedException {
        ConcurrentStack object = new ConcurrentStack();

        final int threadCount = 2;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        executorService.execute(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    object.pushToStack("Item " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.execute(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    logger.info("Item popped " + object.popFromStack());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * 支持并发的stack
     */
    static class ConcurrentStack {
        private Stack<String> stack = new Stack<>();
        private static final int CAPACITY = 5;

        private ReentrantLock lock = new ReentrantLock();
        private Condition stackEmptyCondition = lock.newCondition();
        private Condition stackFullCondition = lock.newCondition();

        private void pushToStack(String item) throws InterruptedException {
            try {
                lock.lock();
                if (stack.size() == CAPACITY) {
                    logger.info(Thread.currentThread().getName() + " wait on full stack");
                    stackFullCondition.await();
                }
                logger.info("Pushing the item " + item);
                stack.push(item);
                stackEmptyCondition.signalAll();
            } finally {
                lock.unlock();
            }

        }

        private String popFromStack() throws InterruptedException {
            try {
                lock.lock();
                if (stack.size() == 0) {
                    logger.info(Thread.currentThread().getName() + " wait on empty stack");
                    stackEmptyCondition.await();
                }
                return stack.pop();
            } finally {
                stackFullCondition.signalAll();
                lock.unlock();
            }
        }
    }




}
