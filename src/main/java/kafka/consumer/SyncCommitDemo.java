package kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 同步提交，会阻塞，直到有返回结果，性能会差一些。
 * @author: zhangzhongkun
 * @create: 2019-03-08 17:16
 **/
public class SyncCommitDemo {
    public static void main(String[] args) {
        final Properties properties = new Properties() {
            {
                put("bootstrap.servers", "172.28.104.11:9092");
                put("group.id", "SyncCommitDemo");
                put("enable.auto.commit", "false");
                put("auto.commit.interval.ms", "1000");
                put("session.timeout.ms", "30000");
                put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                put("auto.offset.reset", "earliest");
            }
        };

        KafkaConsumer consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(""));
        while (true) {
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record : records) {
                System.out.printf("topic = %s, partition = %s, offset" + " = %d, costomer = %s, country = %s \n",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value());
            }
            try {
                consumer.commitSync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
