package com.zzk.study.library.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @description:
 * 一个非常常见的简单消费者实例，但是，会存在以下的问题:
 * //自动提交，会有问题
 * 1.默认会5秒提交一次offset，但是中间停止的话会造成重复消费
 * 2.新添加进消费者组的时候，会再均衡，默认从上次消费提交的地方开始，消息重复
 * 3.自动提交，虽然提交了偏移量，但并不知道，哪些消息被处理了，是否处理成功，偏移量是否提交成功
 *
 * @author: zhangzhongkun
 * @create: 2019-03-08 16:51
 **/
public class AutoCommitDemo {
    public static void main(String[] args) {
        final Properties properties = new Properties() {
            {
                put("bootstrap.servers", "172.28.104.11:9092");
                put("group.id", "AutoCommitDemo");
                put("enable.auto.commit", "true");
                put("auto.commit.interval.ms", "1000");
                put("session.timeout.ms", "30000");
                put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                put("auto.offset.reset", "earliest");
            }
        };
        KafkaConsumer consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList("configToWork"));
        int count=0;
        while (true) {
            ConsumerRecords<String,String> records = consumer.poll(100);
            try {
                Thread.sleep(200);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (ConsumerRecord<String,String> record : records) {
                count++;
                System.out.printf("topic = %s,partition = %d, offset = %d, key = %s, value = %s" + System.lineSeparator(), record.topic(), record.partition(), record.offset(), record.key(), record.value());
            }

        }

    }

}
