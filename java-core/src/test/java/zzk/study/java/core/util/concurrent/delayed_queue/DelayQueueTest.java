package zzk.study.java.core.util.concurrent.delayed_queue;

import org.junit.Test;

import java.util.concurrent.*;

public class DelayQueueTest {
    @Test
    public void test() {
        // 创建延时队列
        DelayQueue<Message> queue = new DelayQueue<>();
        // 添加延时消息,m1 延时3s
        Message m1 = new Message(1, "world", 3000);
        // 添加延时消息,m2 延时10s
        Message m2 = new Message(2, "hello", 10000);
        //将延时消息放到延时队列中
        queue.offer(m2);
        queue.offer(m1);
        // 启动消费线程 消费添加到延时队列中的消息，前提是任务到了延期时间
        ExecutorService exec = Executors.newFixedThreadPool(1);
        exec.execute(new Consumer(queue));
        exec.shutdown();
    }

    /**
     * 消息体定义 实现Delayed接口就是实现两个方法即compareTo 和 getDelay
     * 最重要的就是getDelay方法，这个方法用来判断是否到期……
     */
    public class Message implements Delayed {
        private int id;
        private String body; // 消息内容
        private long excuteTime;// 延迟时长，这个是必须的属性因为要按照这个判断延时时长。

        public int getId() {
            return id;
        }

        public String getBody() {
            return body;
        }

        public long getExcuteTime() {
            return excuteTime;
        }

        public Message(int id, String body, long delayTime) {
            this.id = id;
            this.body = body;
            this.excuteTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
        }

        // 自定义实现比较方法返回 1 0 -1三个参数
        @Override
        public int compareTo(Delayed delayed) {
            Message msg = (Message) delayed;
            return Integer.valueOf(this.id) > Integer.valueOf(msg.id) ? 1
                    : (Integer.valueOf(this.id) < Integer.valueOf(msg.id) ? -1 : 0);
        }

        // 延迟任务是否到时就是按照这个方法判断如果返回的是负数则说明到期否则还没到期
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.excuteTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        }
    }

    public class Consumer implements Runnable {
        // 延时队列 ,消费者从其中获取消息进行消费
        private DelayQueue<Message> queue;

        public Consumer(DelayQueue<Message> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Message take = queue.take();
                    System.out.println("消费消息id：" + take.getId() + " 消息体：" + take.getBody());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}