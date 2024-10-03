package com.zzk.study.agent.demo03;

public class App {
    public static void main(String[] args) throws InterruptedException {
        long pid = ProcessHandle.current().pid();
        System.out.println("Thread Id:" + pid);
        while (true) {
            System.out.println("from main");
            Thread.sleep(2000);
        }
    }
}
