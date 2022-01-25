package zzk.study.java.core.util.concurrent;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import static junit.framework.TestCase.assertEquals;

/**
 * The Phaser allows us to build logic in which threads need to wait on the barrier before going to the next step of execution.
 *
 * To participate in the coordination, the thread needs to register() itself with the Phaser instance.
 * Note that this only increases the number of registered parties, and we can't check whether the current thread is registered –
 * we'd have to subclass the implementation to supports this.
 *
 * The thread signals that it arrived at the barrier by calling the arriveAndAwaitAdvance(), which is a blocking method.
 * When the number of arrived parties is equal to the number of registered parties, the execution of the program will continue,
 * and the phase number will increase. We can get the current phase number by calling the getPhase() method.
 *
 * When the thread finishes its job, we should call the arriveAndDeregister() method to signal that the current thread should
 * no longer be accounted for in this particular phase.
 *
 * @author zhangzhongkun02
 * @date 2021/12/6
 * */
public class PhaserUnitTest {

    @Test
    public void givenPhaser_whenCoordinateWorksBetweenThreads_thenShouldCoordinateBetweenMultiplePhases() {
        //given
        ExecutorService executorService = Executors.newCachedThreadPool();
        Phaser ph = new Phaser(1);
        // equivalent to this
//        Phaser ph = new Phaser();
//        ph.register();
        assertEquals(0, ph.getPhase());

        //when
        executorService.submit(new LongRunningAction("thread-1", ph));
        executorService.submit(new LongRunningAction("thread-2", ph));
        executorService.submit(new LongRunningAction("thread-3", ph));

        //we've initialized our Phaser with 1 and called register() three more times.
        // Now, three action threads have announced that they've arrived at the barrier,
        // so one more call of arriveAndAwaitAdvance() is needed – the one from the main thread:
        ph.arriveAndAwaitAdvance();
        assertEquals(1, ph.getPhase());

        //and
        executorService.submit(new LongRunningAction("thread-4", ph));
        executorService.submit(new LongRunningAction("thread-5", ph));
        ph.arriveAndAwaitAdvance();
        assertEquals(2, ph.getPhase());

        //When the deregistration causes the number of registered parties to become zero, the Phaser is terminated
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

        @SneakyThrows
        @Override
        public void run() {
            System.out.println("This is phase " + ph.getPhase());
            System.out.println("Thread " + threadName + " before long running action");
            ph.arriveAndAwaitAdvance();

            Thread.sleep(20);
            ph.arriveAndDeregister();
        }
    }

}
