package zzk.study.java.core.util.concurrent.thread.threadlifecycle;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 线程的各种状态
 * */
public class StateTest {
    @Test
    public void test_state() throws InterruptedException {
        Runnable runnable = new DemoThread();
        Thread t = new Thread(runnable);
        Assert.assertSame("same", Thread.State.NEW, t.getState());

        t.start();
        Assert.assertSame("same", Thread.State.RUNNABLE, t.getState());

        TimeUnit.SECONDS.sleep(1);
        Assert.assertSame("same", Thread.State.TERMINATED, t.getState());
    }

    @Test
    public void test_block_state() throws InterruptedException {
        Thread t1 = new Thread(new DemoThreadB());
        Thread t2 = new Thread(new DemoThreadB());

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(1);

        Assert.assertSame("same", Thread.State.BLOCKED, t2.getState());
        System.exit(0);
    }

    @Test
    public void test_timed_waiting_state() throws InterruptedException {

        Thread t1 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
        t1.start();
        // The following sleep will give enough time for ThreadScheduler to start processing of thread t1
        TimeUnit.SECONDS.sleep(1);
        Assert.assertSame("same", Thread.State.TIMED_WAITING, t1.getState());
    }

    @Test
    public void test_waiting_state() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Thread t2 = new Thread(new DemoThreadWS());
            t2.start();

            try {
                t2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        },"parent");

        t1.start();
        t1.join();
    }

    static class DemoThread implements Runnable{
        @Override
        public void run() {

        }
    }

    static class DemoThreadB implements Runnable {
        @Override
        public void run() {
            commonResource();
        }

        public static synchronized void commonResource() {
            while(true) {
                // Infinite loop to mimic heavy processing
                // Thread 't1' won't leave this method
                // when Thread 't2' enters this
            }
        }
    }
    


    static class DemoThreadWS implements Runnable {

        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            Assert.assertSame("same", Thread.State.WAITING, getThreadByName("parent").getState());
            System.out.println(getThreadByName("parent").getState());
        }

        public Thread getThreadByName(String threadName) {
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                if (t.getName().equals(threadName)) return t;
            }
            return null;
        }
    }
}