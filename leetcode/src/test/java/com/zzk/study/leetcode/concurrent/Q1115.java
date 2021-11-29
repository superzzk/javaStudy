package com.zzk.study.leetcode.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1115. 交替打印FooBar
 * 我们提供一个类：
 *
 * class FooBar {
 *   public void foo() {
 *     for (int i = 0; i < n; i++) {
 *       print("foo");
 *     }
 *   }
 *
 *   public void bar() {
 *     for (int i = 0; i < n; i++) {
 *       print("bar");
 *     }
 *   }
 * }
 *
 * 两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。
 *
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 *
 *
 * 示例 1:
 *
 * 输入: n = 1
 * 输出: "foobar"
 * 解释: 这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
 *
 * 示例 2:
 *
 * 输入: n = 2
 * 输出: "foobarfoobar"
 * 解释: "foobar" 将被输出两次。
 **/
public class Q1115 {
    class FooBar {
        private int n;

        volatile int printFooTimes = 0;
        volatile int printBarTimes = 0;

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                while(printFooTimes != printBarTimes)
                    Thread.sleep(1);
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                printFooTimes++;
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                while(printFooTimes == printBarTimes)
                    Thread.sleep(1);
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                printBarTimes++;
            }
        }
    }

    /**
     * 使用Semaphore
     */
    class FooBar2 {
        private int n;

        public FooBar2(int n) {
            this.n = n;
        }

        Semaphore foo = new Semaphore(1);
        Semaphore bar = new Semaphore(0);

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                foo.acquire();
                printFoo.run();
                bar.release();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                bar.acquire();
                printBar.run();
                foo.release();
            }
        }
    }

    /**
     * 公平锁
     */
    class FooBar3 {
        private int n;

        public FooBar3(int n) {
            this.n = n;
        }

        Lock lock = new ReentrantLock(true);
        volatile boolean permitFoo = true;

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; ) {
                lock.lock();
                try {
                    if(permitFoo) {
                        printFoo.run();
                        i++;
                        permitFoo = false;
                    }
                }finally {
                    lock.unlock();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; ) {
                lock.lock();
                try {
                    if(!permitFoo) {
                        printBar.run();
                        i++;
                        permitFoo = true;
                    }
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * CyclicBarrier
     */
    class FooBar4 {
        private int n;

        public FooBar4(int n) {
            this.n = n;
        }

        CyclicBarrier cb = new CyclicBarrier(2);
        volatile boolean fin = true;

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                while(!fin);
                printFoo.run();
                fin = false;
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                }
                printBar.run();
                fin = true;
            }
        }
    }

}
