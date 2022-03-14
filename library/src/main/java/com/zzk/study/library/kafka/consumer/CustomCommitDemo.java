package com.zzk.study.library.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 自制定提交
 * 指定每1000条提交一次offset
 * @author: zhangzhongkun
 * @create: 2019-03-08 17:16
 **/
public class CustomCommitDemo {
    public static void main(String[] args) {
        final Properties properties = new Properties() {
            {
                put("bootstrap.servers", "172.28.104.11:9092");
                put("group.id", "AsyncCommitDemo");
                put("enable.auto.commit", "false");
                put("auto.commit.interval.ms", "1000");
                put("session.timeout.ms", "30000");
                put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                put("auto.offset.reset", "earliest");
            }
        };

        KafkaConsumer consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList("configToWork"));

        Map currentOffsets = new HashMap<>();
        int count = 0;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("topic = %s, partition = %s, offset" + " = %d, costomer = %s, country = %s \n",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value());

                currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
                        new OffsetAndMetadata(record.offset() + 1, "no meta"));
                if (count % 1000 == 0) {
                    consumer.commitAsync();
                }
                count++;
            }
        }
    }

}
