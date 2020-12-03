package com.zzk.study.lang;

/**
 * @author zhangzhongkun
 * @since 2019-07-25 15:00
 **/
public class VolatileTest extends  Thread{
    public static volatile int n = 0;

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                n = n + 1;
                Thread.sleep(3);  //  为了使运行结果更随机，延迟3毫秒
            } catch (Exception ignored) {

            }
        }
    }
    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            //  建立100个线程
            threads[i] = new VolatileTest();
        }
        for (Thread thread : threads) {
            //  运行刚才建立的100个线程
            thread.start();
        }
        for (Thread thread : threads) thread.join();
        System.out.println(" n= " + VolatileTest.n);
    }
}
