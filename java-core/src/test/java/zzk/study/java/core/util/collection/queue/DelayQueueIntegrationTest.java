package zzk.study.java.core.util.collection.queue;

import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class DelayQueueIntegrationTest {
    @Test
    public void givenDelayQueue_whenProduceElement_thenShouldConsumeAfterGivenDelay() throws InterruptedException {
        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 2;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer
          = new DelayQueueProducer(queue, numberOfElementsToProduce, 500);

        //when
        executor.submit(producer);
        executor.submit(consumer);

        //then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();
        assertEquals(consumer.numberOfConsumedElements.get(), numberOfElementsToProduce);
    }

    @Test
    public void givenDelayQueue_whenProduceElementWithHugeDelay_thenConsumerWasNotAbleToConsumeMessageInGivenTime() throws InterruptedException {
        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 1;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer
          = new DelayQueueProducer(queue, numberOfElementsToProduce, 10_000);

        //when
        executor.submit(producer);
        executor.submit(consumer);

        //then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();
        assertEquals(consumer.numberOfConsumedElements.get(), 0);

    }

    @Test
    public void givenDelayQueue_whenProduceElementWithNegativeDelay_thenConsumeMessageImmediately() throws InterruptedException {
        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 1;
        int delayOfEachProducedMessageMilliseconds = -10_000;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer
          = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        //when
        executor.submit(producer);
        executor.submit(consumer);

        //then
        executor.awaitTermination(1, TimeUnit.SECONDS);
        executor.shutdown();
        assertEquals(consumer.numberOfConsumedElements.get(), 1);

    }

    public class DelayObject implements Delayed {
        private String data;
        private long startTime;

        DelayObject(String data, long delayInMilliseconds) {
            this.data = data;
            this.startTime = System.currentTimeMillis() + delayInMilliseconds;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = startTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Ints.saturatedCast(this.startTime - ((DelayObject) o).startTime);
        }

        @Override
        public String toString() {
            return "{" + "data='" + data + '\'' + ", startTime=" + startTime + '}';
        }
    }

    public class DelayQueueConsumer implements Runnable {
        private BlockingQueue<DelayObject> queue;
        private final Integer numberOfElementsToTake;
        final AtomicInteger numberOfConsumedElements = new AtomicInteger();

        DelayQueueConsumer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToTake) {
            this.queue = queue;
            this.numberOfElementsToTake = numberOfElementsToTake;
        }

        @Override
        public void run() {
            for (int i = 0; i < numberOfElementsToTake; i++) {
                try {
                    DelayObject object = queue.take();
                    numberOfConsumedElements.incrementAndGet();
                    System.out.println("Consumer take: " + object);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class DelayQueueProducer implements Runnable {
        private BlockingQueue<DelayObject> queue;
        private final Integer numberOfElementsToProduce;
        private final Integer delayOfEachProducedMessageMilliseconds;

        DelayQueueProducer(BlockingQueue<DelayObject> queue,
                           Integer numberOfElementsToProduce,
                           Integer delayOfEachProducedMessageMilliseconds) {
            this.queue = queue;
            this.numberOfElementsToProduce = numberOfElementsToProduce;
            this.delayOfEachProducedMessageMilliseconds = delayOfEachProducedMessageMilliseconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < numberOfElementsToProduce; i++) {
                DelayObject object
                        = new DelayObject(UUID.randomUUID().toString(), delayOfEachProducedMessageMilliseconds);
                System.out.println("Put object = " + object);
                try {
                    queue.put(object);
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }
}