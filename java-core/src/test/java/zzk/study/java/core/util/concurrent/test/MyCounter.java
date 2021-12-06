package zzk.study.java.core.util.concurrent.test;

public class MyCounter {

    private int count;

    public void increment() {
        int temp = count;
        count = temp + 1;
    }

    /*
    * control the interleaving of threads so that we can reveal concurrency issues in a deterministic manner with much fewer threads
    * */
    public synchronized void incrementWithWait() throws InterruptedException {
        int temp = count;
        wait(100);
        count = temp + 1;
    }

    public int getCount() {
        return count;
    }

}
