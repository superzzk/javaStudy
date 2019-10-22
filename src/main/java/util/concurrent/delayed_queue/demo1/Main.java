package util.concurrent.delayed_queue.demo1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author zhangzhongkun
 * @since 2019-07-31 11:22
 **/
public class Main {

    public static void main(String[] args) throws InterruptedException {
        test1();
        test_timeOut();
    }

    private static void test1() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 2;
        int delayOfEachProducedMessageMilliseconds = 500;

        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        executor.submit(producer);
        executor.submit(consumer);

        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();
        System.out.println("消费：" + consumer.numberOfConsumedElements.get());
        assertEquals(consumer.numberOfConsumedElements.get(), numberOfElementsToProduce);
    }

    private static void test_timeOut() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 2;
        int delayOfEachProducedMessageMilliseconds = 10000;

        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        executor.submit(producer);
        executor.submit(consumer);

        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();
        System.out.println("消费：" + consumer.numberOfConsumedElements.get());
        assertEquals(consumer.numberOfConsumedElements.get(), 0);
    }
}
