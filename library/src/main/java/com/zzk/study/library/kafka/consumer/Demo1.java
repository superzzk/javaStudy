package com.zzk.study.library.kafka.consumer;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-02-12 14:30
 **/
public class Demo1 {
    private static final Logger log = LoggerFactory.getLogger(Demo1.class);

    KafkaConsumer<String, String> consumer;

    public Demo1() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,broker2:9092");
        props.put("group.id", "CountryCounter");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String, String>(props);
    }

    public static void main(String args[]){
        Demo1 demo = new Demo1();
        demo.test1();
    }

    void test1(){
        Map<String, Integer> custCountryMap = new HashMap<>();

        consumer.subscribe(Collections.singletonList("customerCountries"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records)
                {
                    log.info("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n",
                    record.topic(), record.partition(), record.offset(),
                            record.key(), record.value());
                    int updatedCount = 1;
                    if (custCountryMap.containsValue(record.value())) {
                        updatedCount = custCountryMap.get(record.value()) + 1;
                    }
                    custCountryMap.put(record.value(), updatedCount);

                    System.out.println( JSONObject.toJSONString(custCountryMap) );
                }
            }
        } finally {
            consumer.close();
        }
    }
}
