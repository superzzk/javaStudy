package util;

import java.util.concurrent.*;

public class ExecutorDemo {
    public static void main(String[] args) {
//        demo1();
//        demo2();
        demo3();
    }

    //newFixedThreadPool
    public static void demo1() {
        // Create a fixed thread pool with maximum three threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit runnable tasks to the executor
        executor.execute(new PrintChar('a', 100));
        executor.execute(new PrintChar('b', 100));
        executor.execute(new PrintNum(100));

        // Shutdown the executor
        executor.shutdown();
    }

    //newFixedThreadPool
    public static void demo2() {
        // Create a fixed thread pool with maximum three threads
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // Submit runnable tasks to the executor
        executor.execute(new PrintChar('a', 100));
        executor.execute(new PrintChar('b', 100));
        executor.execute(new PrintNum(100));

        // Shutdown the executor
        executor.shutdown();
    }

    //newCachedThreadPool
    public static void demo3() {
        // Create a fixed thread pool with maximum three threads
        ExecutorService executor = Executors.newCachedThreadPool();

        // Submit runnable tasks to the executor
        executor.execute(new PrintChar('a', 100));
        executor.execute(new PrintChar('b', 100));
        executor.execute(new PrintNum(100));

        // Shutdown the executor
        executor.shutdown();
    }
}
