package com.zzk.study.library.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Demo1 {

    KafkaProducer<String, String> producer;

    public Demo1() {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092,broker2:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(kafkaProps);
    }

    public static void main(String args[]) {

        Demo1 demo = new Demo1();
        demo.sendSync();

    }

    void sendDemo1() {
        ProducerRecord<String, String> record =
                new ProducerRecord<>("customerCountries", "Precision Products", "France");
        try {
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendSync() {
        ProducerRecord<String, String> record =
                new ProducerRecord<>("customerCountries", "Precision Products", "France");
        try {
            RecordMetadata rt = producer.send(record).get();
            System.out.println("topic:" + rt.topic() + "  offset:" + rt.offset() + "  partition:" + rt.partition());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步回调不阻塞
     **/
    void sendAsync() {

        ProducerRecord<String, String> record =
                new ProducerRecord<>("CustomerCountry", "Biomedical Materials", "USA");
        producer.send(record, new DemoProducerCallback());
    }

    private class DemoProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                e.printStackTrace();
            } else {
                System.out.println("The offset of the record we just sent is: " + recordMetadata.offset());
            }
        }
    }

    /**
     * 同步回调阻塞
     **/
    void sendTrans() {
        ProducerRecord<String, String> record =
                new ProducerRecord<>("CustomerCountry", "Biomedical Materials", "USA");
        try {
            producer.send(record).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 事务和幂等
     * From Kafka 0.11, the KafkaProducer supports two additional modes: the idempotent producer
     * and the transactional producer. The idempotent producer strengthens Kafka’s delivery
     * semantics from at least once to exactly once delivery. In particular producer retries will
     * no longer introduce duplicates. The transactional producer allows an application to send
     * messages to multiple partitions (and topics!) atomically.
     *
     * To enable idempotence, the enable.idempotence configuration must be set to true. If set,
     * the retries config will default to Integer.MAX_VALUE and the acks config will default to all.
     * There are no API changes for the idempotent producer, so existing applications will not need
     * to be modified to take advantage of this feature.
     *
      **/
    void sendAsyncBlock() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("transactional.id", "my-transactional-id");
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
        producer.initTransactions();
        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++)
                producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)));
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e)
        { // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (KafkaException e) { // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
        producer.close();

    }
}
