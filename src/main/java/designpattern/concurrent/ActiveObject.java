package designpattern.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;

public class ActiveObject {

    class MyActiveObject {

        private double val = 0.0;
        private BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();

        public MyActiveObject() {
            new Thread (new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            dispatchQueue.take().run();
                        } catch (InterruptedException e) {
                            // okay, just terminate the dispatcher
                        }
                    }
                }
            }
            ).start();
        }

        void doSomething() throws InterruptedException {
            dispatchQueue.put(new Runnable() {
                                  @Override
                                  public void run() {
                                      val = 1.0;
                                  }
                              }
            );
        }

        void doSomethingElse() throws InterruptedException {
            dispatchQueue.put(new Runnable() {
                                  @Override
                                  public void run() {
                                      val = 2.0;
                                  }
                              }
            );
        }
    }

    // Java 8
    public class MyClass2 {
        private double val;

        // container for tasks
        // decides which request to execute next
        // asyncMode=true means our worker thread processes its local task queue in the FIFO order
        // only single thread may modify internal state
        private final ForkJoinPool fj = new ForkJoinPool(1,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);

        // implementation of active object method
        public void doSomething() throws InterruptedException {
            fj.execute(() -> { val = 1.0; });
        }

        // implementation of active object method
        public void doSomethingElse() throws InterruptedException {
            fj.execute(() -> { val = 2.0; });
        }
    }
}
