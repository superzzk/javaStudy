package com.zzk.study.agent;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Thread Id:" + Thread.currentThread().getId());
        while (true) {
            System.out.println("from main");
            Thread.sleep(2000);
        }
    }
}
