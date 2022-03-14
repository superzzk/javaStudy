package com.zzk.study.library.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * 异步提交的特性：与同步提交不同的是，遇到错误，commitSync会一直重试，但是commitAsync不会，
 * 原因，很简单，如果异步提交还重试，会存在一个问题，a提交2000的偏移量，网络问题，一直重试，
 * 但下一个3000的提交成功，这时候，2000的ok了，就会造成消息重复。
 *
 * @author: zhangzhongkun
 * @create: 2019-03-08 17:16
 **/
public class AsyncCommitDemo {
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
        while (true) {
            ConsumerRecords<String, String>  records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("topic = %s, partition = %s, offset" + " = %d, costomer = %s, country = %s \n",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value());
            }
            try {//consumer.commitAsync();//发送提交请求，提交失败就纪录下来
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map map, Exception e) {
                        if (e != null) {
                            e.printStackTrace();
                            System.out.println("commit failed"+map);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
