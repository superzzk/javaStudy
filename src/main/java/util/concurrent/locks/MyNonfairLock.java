package util.concurrent.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 非公平锁对锁的获取是乱序的，即有一个抢占锁的过程
 */
public class MyNonfairLock {
    /**
     *     false 表示 ReentrantLock 的非公平锁
     */
    private ReentrantLock lock = new ReentrantLock(false);

    public  void testFail(){
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() +"获得了锁");
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        MyNonfairLock nonfairLock = new MyNonfairLock();

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName()+"启动");
            nonfairLock.testFail();
        };
        Thread[] threadArray = new Thread[10];
        for (int i=0; i<10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i=0; i<10; i++) {
            threadArray[i].start();
        }
    }
}