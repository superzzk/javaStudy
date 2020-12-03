package com.zzk.study.util.concurrent.delayed_queue.demo1;

import com.google.common.primitives.Ints;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Each element we want to put into the DelayQueue needs to implement the Delayed interface.
 * Let’s say that we want to create a DelayObject class.
 * Instances of that class will be put into the DelayQueue.
 **/
public class DelayObject implements Delayed {
    private String data;

    //this is a time when the element should be consumed from the queue.
    private long startTime;


    public DelayObject(String data, long delayInMilliseconds) {
        this.data = data;
        this.startTime = System.currentTimeMillis() + delayInMilliseconds;
    }

    /**
     * return the remaining delay associated with this object in the given time unit.
     * <p>
     * When the consumer tries to take an element from the queue,
     * the DelayQueue will execute getDelay() to find out if that element is allowed to be returned from the queue.
     * If the getDelay() method will return zero or a negative number, it means that it could be retrieved from the queue.
     */
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * the elements in the DelayQueue will be sorted according to the expiration time.
     * The item that will expire first is kept at the head of the queue and the element
     * with the highest expiration time is kept at the tail of the queue
     */
    @Override
    public int compareTo(Delayed o) {
        return Ints.saturatedCast(
                this.startTime - ((DelayObject) o).startTime);
    }
}