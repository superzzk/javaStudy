package zzk.study.java.core.util.concurrent.transferqueue;

import org.junit.Test;

import java.util.concurrent.*;

import static junit.framework.TestCase.assertEquals;
/**
 * Simply put, this queue allows us to create programs according to the producer-consumer pattern,
 * and coordinate messages passing from producers to consumers.
 *
 * The implementation is actually similar to the BlockingQueue
 * – but gives us the new ability to implement a form of backpressure.
 * This means that, when the producer sends a message to the consumer using the transfer() method,
 * the producer will stay blocked until the message is consumed.
 */
public class TransferQueueIntegrationTest {

    @Test
    public void whenMultipleConsumersAndProducers_thenProcessAllMessages() throws InterruptedException {
        //given
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(3);
        Producer producer1 = new Producer(transferQueue, "1", 3);
        Producer producer2 = new Producer(transferQueue, "2", 3);
        Consumer consumer1 = new Consumer(transferQueue, "1", 3);
        Consumer consumer2 = new Consumer(transferQueue, "2", 3);

        //when
        exService.execute(producer1);
        exService.execute(producer2);
        exService.execute(consumer1);
        exService.execute(consumer2);

        //then
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();

        assertEquals(producer1.numberOfProducedMessages.intValue(), 3);
        assertEquals(producer2.numberOfProducedMessages.intValue(), 3);
    }

    @Test
    public void whenUseOneConsumerAndOneProducer_thenShouldProcessAllMessages() throws InterruptedException {
        //given
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new Producer(transferQueue, "1", 3);
        Consumer consumer = new Consumer(transferQueue, "1", 3);

        //when
        exService.execute(producer);
        exService.execute(consumer);

        //then
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();

        assertEquals(producer.numberOfProducedMessages.intValue(), 3);
        assertEquals(consumer.numberOfConsumedMessages.intValue(), 3);
    }

    @Test
    public void whenUseOneProducerAndNoConsumers_thenShouldFailWithTimeout() throws InterruptedException {

        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new Producer(transferQueue, "1", 3);

        exService.execute(producer);

        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();

        assertEquals(producer.numberOfProducedMessages.intValue(), 0);
    }
}