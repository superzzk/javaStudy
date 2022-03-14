package com.zzk.study.library.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 *
 * @author yujie.wang
 * kafka生产者示例代码
 */
public class Producer_Sample {
    //kafka集群机器
    private static final String KAFKA_HOSTS = "localhost:9092";
    //topic名称
    private static final String TOPIC = "test";

    public static void main(String[] args) {
        Producer_Sample producer = new Producer_Sample();
        producer.producer_send(TOPIC);
        System.out.println("end");
    }

    /**
     * 生产者生产数据
     * 发送消息是异步进行，一旦消息被保存到分区缓存中，send方法就返回
     * 一旦消息被接收 就会调用callBack
     * @param topic
     */
    public void producer_send(String topic){
        Properties props = new Properties();
        //kafka集群机器
        props.put("bootstrap.servers", KAFKA_HOSTS);
        //生产者发送的数据需要等待主分片和其副本都保存才发回确认消息
        props.put("acks", "all");
        //生产者发送失败后的确认消息
        props.put("retries", 0);
        //生产者 每个分区缓存大小 16K
        props.put("batch.size", 16384);
        //生产者发送分区缓存中数据前停留时间
        props.put("linger.ms", 1);
        //生产者可用缓存总量大小 32M
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String,String>(props);
        for(int i = 220; i < 230; i++){
            //发送消息是异步进行，一旦消息被保存到分区缓存中，send方法就返回
            // producer.send(new ProducerRecord<String, String>("my-replicated-topic_1", Integer.toString(i), Integer.toString(i)));
            producer.send(new ProducerRecord<String, String>(
                    topic, "call___"+Integer.toString(i+20), "call___"+Integer.toString(i)),
                    new Call());
            System.out.println("send return I: "+ i);
        }

        producer.close();
    }

    /**
     *消息被保存之后的回调方法
     */
    class Call implements Callback {

        @Override
        public void onCompletion(RecordMetadata recordmetadata,
                                 Exception exception) {
            System.out.println("callBack: "+ recordmetadata.checksum() + " recordmetadata content : "+recordmetadata.toString());
        }

    }
}