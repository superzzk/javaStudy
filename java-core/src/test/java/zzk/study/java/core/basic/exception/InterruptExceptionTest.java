package zzk.study.java.core.basic.exception;

import org.junit.jupiter.api.Test;

/**
 * https://www.javaspecialists.eu/archive/Issue056-Shutting-down-Threads-Cleanly.html
 *
 * @author zhangzhongkun02
 * @date 2023/5/5 1:45 PM
 */
public class InterruptExceptionTest {
    /**
     * bad ideal
     * 不需要单独使用状态标识线程状态，本身已经提供了interrupt机制
     * */
    public class UsingFlagToShutdownThread extends Thread {
        private volatile boolean running = true;
        public void run() {
            while (running) {
                System.out.print(".");
                System.out.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}
            }
            System.out.println("Shutting down thread");
        }
        public void shutdown() {
            running = false;
        }
    }
    @Test
    public  void bad_idea() throws InterruptedException {
        UsingFlagToShutdownThread t = new UsingFlagToShutdownThread();
        t.start();
        Thread.sleep(5000);
        t.shutdown();
    }

    public class UsingInterruptToShutdownThread extends Thread {
        public void run() {
            while (true) {
                System.out.print(".");
                System.out.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); // very important
                    break;
                }
            }
            System.out.println("Shutting down thread");
        }
    }
    @Test
    public  void solution() throws InterruptedException {
        Thread t = new UsingInterruptToShutdownThread();
        t.start();
        Thread.sleep(5000);
        t.interrupt();
    }

    /**
     * if the thread is interrupted before it blocks on Object.wait(..) or Thread.sleep(..) etc.,
     * this is equivalent to it being interrupted immediately upon blocking on that method
     * */
    @Test
    public void demo() {
        Thread.currentThread().interrupt();
        printInterrupted(1);
        Object o = new Object();
        try {
            synchronized (o) {
                printInterrupted(2);
                System.out.printf("A Time %d\n", System.currentTimeMillis());
                o.wait(100);
                System.out.printf("B Time %d\n", System.currentTimeMillis());
            }
        } catch (InterruptedException ie) {
            System.out.printf("WAS interrupted\n");
        }
        System.out.printf("C Time %d\n", System.currentTimeMillis());
        printInterrupted(3);
        Thread.currentThread().interrupt();
        printInterrupted(4);
        try {
            System.out.printf("D Time %d\n", System.currentTimeMillis());
            Thread.sleep(100);
            System.out.printf("E Time %d\n", System.currentTimeMillis());
        } catch (InterruptedException ie) {
            System.out.printf("WAS interrupted\n");
        }
        System.out.printf("F Time %d\n", System.currentTimeMillis());
        printInterrupted(5);
        try {
            System.out.printf("G Time %d\n", System.currentTimeMillis());
            Thread.sleep(100);
            System.out.printf("H Time %d\n", System.currentTimeMillis());
        } catch (InterruptedException ie) {
            System.out.printf("WAS interrupted\n");
        }
        System.out.printf("I Time %d\n", System.currentTimeMillis());

    }
    private void printInterrupted(int n) {
        System.out.printf("(%d) Am I interrupted? %s\n", n,
                Thread.currentThread().isInterrupted() ? "Yes" : "No");
    }
}
