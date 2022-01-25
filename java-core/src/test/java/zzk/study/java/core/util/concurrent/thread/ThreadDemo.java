package zzk.study.java.core.util.concurrent.thread;

import org.junit.Test;

import java.lang.management.ManagementFactory;

public class ThreadDemo {

    @Test
    public void thread_info(){
        Thread t = new Thread();
        System.out.println("thread group: " + t.getThreadGroup());
        System.out.println("thread group name: " + t.getThreadGroup().getName());
        System.out.println("is interrupted: " + t.isInterrupted());
        System.out.println("stack trace: " + t.getStackTrace());
    }

    @Test
    public void find_number_of_threads(){
        System.out.println("Number of threads " + Thread.activeCount());
        System.out.println("Total Number of threads "
                + ManagementFactory.getThreadMXBean().getThreadCount());
    }
}
