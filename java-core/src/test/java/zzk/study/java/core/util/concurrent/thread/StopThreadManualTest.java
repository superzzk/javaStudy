package zzk.study.java.core.util.concurrent.thread;

//import com.jayway.awaitility.Awaitility;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

//import static com.jayway.awaitility.Awaitility.await;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StopThreadManualTest {

    @Test
    public void whenStoppedThreadIsStopped() throws InterruptedException {

        int interval = 5;

        ControlSubThread controlSubThread = new ControlSubThread(interval);
        controlSubThread.start();

        // Give things a chance to get set up
        Thread.sleep(interval);
        assertTrue(controlSubThread.isRunning());
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.stop();

        TimeUnit.MILLISECONDS.sleep(100);
        assertTrue(controlSubThread.isStopped());
    }

    @Test
    public void whenInterruptedThreadIsStopped() throws InterruptedException {

        int interval = 50;

        ControlSubThread controlSubThread = new ControlSubThread(interval);
        controlSubThread.start();

        // Give things a chance to get set up
        Thread.sleep(interval);
        assertTrue(controlSubThread.isRunning());
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.interrupt();

        // Wait less than the time we would normally sleep, and make sure we exited.
//        Awaitility.await()
//            .pollDelay(2, TimeUnit.MILLISECONDS)
//          .atMost(interval/ 10, TimeUnit.MILLISECONDS)
//          .until(controlSubThread::isStopped);
    }

    public class ControlSubThread implements Runnable {

        private Thread worker;
        private int interval = 100;
        private AtomicBoolean running = new AtomicBoolean(false);
        private AtomicBoolean stopped = new AtomicBoolean(true);


        public ControlSubThread(int sleepInterval) {
            interval = sleepInterval;
        }

        public void start() {
            worker = new Thread(this);
            worker.start();
        }

        public void stop() {
            running.set(false);
        }

        public void interrupt() {
            running.set(false);
            worker.interrupt();
        }

        boolean isRunning() {
            return running.get();
        }

        boolean isStopped() {
            return stopped.get();
        }

        public void run() {
            running.set(true);
            stopped.set(false);
            while (running.get()) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted, Failed to complete operation");
                }
                // do something
            }
            stopped.set(true);
        }
    }
}
