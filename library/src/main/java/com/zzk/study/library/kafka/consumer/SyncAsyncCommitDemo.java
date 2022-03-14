package com.zzk.study.library.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Arrays;
import java.util.Properties;

/**
 * 同步提交和异步提交使用组合的方式进行提交，但，这还是会存在一些问题。
 * 因为提交都是批量提交的，但是有可能在批量处理没完成，偏移量没完成的时候，出错了
 *
 * @author: zhangzhongkun
 * @create: 2019-03-08 17:16
 **/
public class SyncAsyncCommitDemo {
    public static void main(String[] args) {
        final Properties properties = new Properties() {
            {
                put("bootstrap.servers", "172.28.104.11:9092");
                put("group.id", "SyncAsyncCommitDemo");
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

        try {
            while (true) {
                ConsumerRecords<String,String> records = consumer.poll(100);
                for (ConsumerRecord<String,String> record : records) {
                    System.out.printf("topic = %s, partition = %s, offset" + " = %d, costomer = %s, country = %s \n",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
                }
                consumer.commitAsync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                consumer.commitSync();
            } catch (Exception e) {
                consumer.close();
            }
        }
    }

}
