package zzk.study.java.agent.sample;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Thread Id:" + Thread.currentThread().threadId());
        while (true) {
            System.out.println("from main");
            Thread.sleep(2000);
        }
    }
}
