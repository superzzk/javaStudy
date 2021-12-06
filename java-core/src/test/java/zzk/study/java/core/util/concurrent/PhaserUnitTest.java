package zzk.study.java.core.util.concurrent;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhaserUnitTest {

    @Test
    public void givenPhaser_whenCoordinateWorksBetweenThreads_thenShouldCoordinateBetweenMultiplePhases() {
        //given
        ExecutorService executorService = Executors.newCachedThreadPool();
        Phaser ph = new Phaser(1);
        assertEquals(0, ph.getPhase());

        //when
        executorService.submit(new LongRunningAction("thread-1", ph));
        executorService.submit(new LongRunningAction("thread-2", ph));
        executorService.submit(new LongRunningAction("thread-3", ph));

        //then
        ph.arriveAndAwaitAdvance();
        assertEquals(1, ph.getPhase());

        //and
        executorService.submit(new LongRunningAction("thread-4", ph));
        executorService.submit(new LongRunningAction("thread-5", ph));
        ph.arriveAndAwaitAdvance();
        assertEquals(2, ph.getPhase());


        ph.arriveAndDeregister();
    }
    static class LongRunningAction implements Runnable {
        private String threadName;
        private Phaser ph;

        LongRunningAction(String threadName, Phaser ph) {
            this.threadName = threadName;
            this.ph = ph;
            ph.register();
        }

        @Override
        public void run() {
            System.out.println("This is phase " + ph.getPhase());
            System.out.println("Thread " + threadName + " before long running action");
            ph.arriveAndAwaitAdvance();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ph.arriveAndDeregister();
        }
    }

}
