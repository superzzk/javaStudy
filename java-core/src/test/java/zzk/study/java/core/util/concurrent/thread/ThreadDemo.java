package zzk.study.java.core.util.concurrent.thread;

import org.junit.Test;

import java.lang.management.ManagementFactory;

public class ThreadDemo {
    @Test
    public void find_number_of_threads(){
        System.out.println("Number of threads " + Thread.activeCount());
        System.out.println("Current Thread Group - "
                + Thread.currentThread().getThreadGroup().getName());
        System.out.println("Total Number of threads "
                + ManagementFactory.getThreadMXBean().getThreadCount());
    }
}
