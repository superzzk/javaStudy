package com.zzk.study.library.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-03-11 10:45
 **/
public class RebalanceListenDemo {

    public static void main(String[] args) {
        Map currentOffsets = new HashMap<>();
        final Properties properties = new Properties() {
            {
                put("bootstrap.servers", "172.28.104.11:9092");
                put("group.id", "RebalanceListenDemo");
                put("enable.auto.commit", "false");
                put("auto.commit.interval.ms", "1000");
                put("session.timeout.ms", "30000");
                put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                put("auto.offset.reset", "earliest");
            }
        };
        KafkaConsumer consumer = new KafkaConsumer <>(properties);
        class HandleRebalace implements ConsumerRebalanceListener {
            @Override public void onPartitionsRevoked(Collection collection) {
            }
            @Override public void onPartitionsAssigned(Collection collection) {
                System.out.println("partition rebalance offset is " + currentOffsets);
                consumer.commitSync(currentOffsets);
            }
        } try {
            consumer.subscribe(Arrays.asList("configToWork"), new HandleRebalace());
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("topic = %s, partition = %s, offset" + " = %d, costomer = %s, country = %s \n",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());

                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1, "no meta"));
                }
                consumer.commitAsync(currentOffsets, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                consumer.commitSync(currentOffsets);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                consumer.close();
            }
        }
    }


}
