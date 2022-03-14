package com.zzk.study.library.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * kafka消费者示例，包含随机位置消费和最多一次消费方式
 * 消费者提交消费数据offset 分为自动提交和手动控制提交
 *
 * 这份代码示例中包含了 多种从kafka的任意位置获取数据的方式
 */
public class Consumer_Sample {

    //kafka集群机器
    private static final String KAFKA_HOSTS = "localhost:9092";
    //topic名称
    private static final String TOPIC = "test";

    public static void main(String[] args) {
        Consumer_Sample consumer = new Consumer_Sample();
        //从分区的末尾 或者已存在groupid的请情况下从未消费位置开始消费数据
//        consumer.consumerSubscribe("true", TOPIC);
        // 通过实现ConsumerRebalanceListener接口 进而时间任意位置的消费
//        consumer.consumerSubscribeImplListener("true", TOPIC);
        //从指定的分区  开始位置seekToBeginning 或者任意位置seek消费数据
        consumer.consumerAssin("true", TOPIC);
        //通过配置属性auto.offset.reset 来设置消费者从分区开头或者末尾进行消费，但是需要使用一定条件的group Id
        consumer.consumerAutoOffsetReset("true", TOPIC);
        System.out.println("consumer end");
    }

    /**
     * 直接通过订阅一个指定分区来消费数据
     * (1)如果该groupId消费者分组下 有消费者提交过offset,则从 当前提交的offset位置开始消费数据
     * (2)如果该groupId消费者分组下 没有有消费者提交过offset,则从 当前log添加的最后位置(也就是数据的末尾)开始消费数据
     * @param isAutoCommitBool
     * @param topic
     */
    public void consumerSubscribe(final String isAutoCommitBool, final String topic){
        Properties props = new Properties();
        //配置kafka集群机器
        props.put("bootstrap.servers", KAFKA_HOSTS);
        //消费者分组
        props.put("group.id", "Consumer_Sample");
        //这里设置 消费者自动提交已消费消息的offset
        props.put("enable.auto.commit", isAutoCommitBool);
        // 设置自动提交的时间间隔为1000毫秒
        props.put("auto.commit.interval.ms", "1000");
        // 设置每次poll的最大数据个数
        props.put("max.poll.records", 5);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅topic
        consumer.subscribe(Arrays.asList(topic));
        List<PartitionInfo> parList = consumer.partitionsFor(topic);

        //打印出分区信息
        printPartition(parList);

        //消费数据
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(5000);
            System.out.println("topic: "+topic + " pool return records size: "+ records.count());
            for (ConsumerRecord<String, String> record : records){
                System.out.println(record.toString());
                //手动提交已消费数据的offset
                if("false".equalsIgnoreCase(isAutoCommitBool)){
                    consumer.commitSync();
                }
            }
        }
    }


    /**
     *
     * @param isAutoCommitBool true 开启自动提交offset;false 不开启
     * @param topic
     * (1)如果该groupId消费者分组下 有消费者提交过offset,则从 当前提交的offset位置开始消费数据
     * (2)如果该groupId消费者分组下 没有有消费者提交过offset,则从 当前log添加的最后位置(也就是数据的末尾)开始消费数据
     *
     * 注意如果enable.auto.commit 设置为false，如果消费完数据没有提交已消费数据的offset，
     * 则会出现重复消费数据的情况
     *
     * 通过实现ConsumerRebalanceListener接口中的onPartitionsAssigned方法，并在其中调用消费者的seek或者seekToBeginning
     * 方法定位分区的任意位置或者开头位置
     */
    public void consumerSubscribeImplListener(final String isAutoCommitBool, final String topic){
        Properties props = new Properties();
        //配置kafka集群机器
        props.put("bootstrap.servers", KAFKA_HOSTS);
        //消费者分组
        props.put("group.id", "yujie26");
        //这里设置 消费者自动提交已消费消息的offset
        props.put("enable.auto.commit", isAutoCommitBool);
        // 设置自动提交的时间间隔为1000毫秒
        props.put("auto.commit.interval.ms", "1000");
        // 设置每次poll的最大数据个数
        props.put("max.poll.records", 5);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅topic,并实现ConsumerRebalanceListener
        consumer.subscribe(Arrays.asList(topic), new ConsumerRebalanceListener(){
            @Override
            public void onPartitionsRevoked(//分区撤销时，消费者可以向该分区提交自己当前的offset
                                            Collection<TopicPartition> collection) {
                if("false".equalsIgnoreCase(isAutoCommitBool)){
                    //consumer.commitSync();
                }
            }

            @Override
            public void onPartitionsAssigned(//当分区分配给消费者时，消费者可以通过该方法重新定位需要消费的数据位置
                                             Collection<TopicPartition> collection) {
                //将消费者定位到各个分区的开始位置进行消费
		/*		consumer.seekToBeginning(util.collection);
				System.out.println("seek beg");*/

                Iterator it = collection.iterator();
                while(it.hasNext()){
                    //将消费者定位到指定分区的指定位置7进行消费
                    consumer.seek((TopicPartition)it.next(), 7);
                }
            }
        });
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(5000);
            System.out.println("topic: "+topic + " pool return records size: "+ records.count());
            for (ConsumerRecord<String, String> record : records){
                System.out.println(record.toString());
                //手动提交已消费数据的offset
                if("false".equalsIgnoreCase(isAutoCommitBool)){
                    consumer.commitSync();
                }
            }
        }
    }


    /**
     *
     * @param isAutoCommitBool true 开启自动提交offset;false 不开启
     * @param topic
     * 如果groupId之前存在 ， 则从之前提交的最后消费数据的offset处继续开始消费数据
     * 如果groupId之前不存在，则从当前分区的最后位置开始消费
     *
     * 注意如果enable.auto.commit 设置为false，如果消费完数据没有提交已消费数据的offset，
     * 则会出现重复消费数据的情况
     */
    public void consumerAutoOffsetReset(final String isAutoCommitBool, final String topic){
        Properties props = new Properties();
        //配置kafka集群机器
        props.put("bootstrap.servers", KAFKA_HOSTS);
        //消费者分组
        props.put("group.id", "yujie32");
        //这里设置 消费者自动提交已消费消息的offset
        props.put("enable.auto.commit", isAutoCommitBool);
        // 设置自动提交的时间间隔为1000毫秒
        props.put("auto.commit.interval.ms", "1000");
        // 设置每次poll的最大数据个数
        props.put("max.poll.records", 5);
        //设置使用最开始的offset偏移量为该group.id的最早。如果不设置，则会是latest即该topic最新一个消息的offset
        //如果采用latest，消费者只能得道其启动后，生产者生产的消息
        //一般配置earliest 或者latest 值
        props.put("auto.offset.reset", "latest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅topic,并实现ConsumerRebalanceListener
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(5000);
            System.out.println("topic: "+topic + "pool return records size: "+ records.count());
            for (ConsumerRecord<String, String> record : records){
                System.out.println(record.toString());
                //手动提交已消费数据的offset
                if("false".equalsIgnoreCase(isAutoCommitBool)){
                    consumer.commitSync();
                }
            }
        }
    }


    /**
     * 通过assign分配的分区,消费者发生故障 Server端不会触发分区重平衡(即使该消费者共享某个已有的groupId)，每个消费者都是独立工作的
     * 为了避免offset提交冲突，需要确保每个消费者都有唯一的groupId
     * 从指定的分区的开头开始消费数据
     * @param isAutoCommitBool true 开启自动提交offset;false 不开启
     * @param topic
     */
    public void consumerAssin(String isAutoCommitBool,String topic){
        Properties props = new Properties();
        //配置kafka集群机器
        props.put("bootstrap.servers", KAFKA_HOSTS);
        //消费者分组
        props.put("group.id", "yujie35");
        //这里设置 消费者自动提交已消费消息的offset
        props.put("enable.auto.commit", isAutoCommitBool);
        // 设置自动提交的时间间隔为1000毫秒
        props.put("auto.commit.interval.ms", "1000");
        // 设置每次poll的最大数据个数
        props.put("max.poll.records", 5);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //获得topic的所有分区
        List<PartitionInfo> parList = consumer.partitionsFor(topic);
        //打印出分区信息
        printPartition(parList);

        List<TopicPartition> list = new ArrayList<>();
        for(PartitionInfo par : parList){
            TopicPartition partition = new TopicPartition(topic, par.partition());
            list.add(partition);
        }
        //消费者指定要消费的分区，指定分区之后消费者崩溃之后 不会引发分区reblance
        consumer.assign(list);

        //从list中所有分区的开头开始消费数据，这个操作不改变已提交的消费数据的offset
        // consumer.seekToBeginning(list);

	     for(TopicPartition tpar:list ){
	    	 consumer.seek(tpar, 4);
	     }

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(5000);
            System.out.println("topic: "+topic + " pool return records size: "+ records.count());
            for (ConsumerRecord<String, String> record : records){
                System.out.println(record.toString());
                //手动提交已消费数据的offset
                if("false".equalsIgnoreCase(isAutoCommitBool)){
                    consumer.commitSync();
                }
            }
        }
    }

    private void printPartition(List<PartitionInfo> parList){
        for(PartitionInfo p : parList){
            System.out.println(p.toString());
        }
    }

    /**
     * 单独处理每个分区中的数据，处理完了之后异步提交offset,注意提交的offset是程序将要读取的下一条消息的offset
     * @param consumer
     */
    public void handlerData(KafkaConsumer<String, String> consumer){
        boolean running = true;
        try {
            while(running) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                    for (ConsumerRecord<String, String> record : partitionRecords) {
                        System.out.println(record.offset() + ": " + record.value());
                    }
                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    //注意提交的offset是程序将要读取的下一条消息的offset
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 关闭消费者
     * @param consumer
     */
    public void closeConsumer(KafkaConsumer<String, String> consumer){
        if(consumer != null){
            consumer.close();
        }
    }

}